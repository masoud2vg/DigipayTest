package com.digipay.payment.transaction;

import com.digipay.payment.common.amqp.SmsResultMessage;
import com.digipay.payment.common.amqp.TransactionResultMessage;
import com.digipay.payment.card.CardService;
import com.digipay.payment.common.exception.PaymentException;
import com.digipay.payment.entity.*;
import com.digipay.payment.entity.Transaction;
import com.digipay.payment.receiver.Sender;
import com.digipay.payment.transaction.api.BankService;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardService cardService;
    private final List<BankService> bankServices;
    private final ModelMapper modelMapper;
    private final Sender mySender;

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, CardService cardService, List<BankService> bankServices, ModelMapper modelMapper, Sender mySender) {
        this.transactionRepository = transactionRepository;
        this.cardService = cardService;
        this.bankServices = bankServices;
        this.modelMapper = modelMapper;
        this.mySender = mySender;
    }

    @Override
    public TransactionDTO transfer(Long userId, TransferDTO transferDTO) throws PaymentException.NotFound {
        Map<String, String> responseBody = new HashMap<>();
        ResponseEntity responseEntity = new ResponseEntity(responseBody, HttpStatus.BAD_REQUEST);
        Optional<Card> cardOptional = cardService.findCard(userId, transferDTO.getSource());
        if (!cardOptional.isPresent())
            throw new PaymentException.NotFound("the source card is not in database");

        Transaction transaction = new Transaction();
        transaction.setCard(cardOptional.get());
        transaction.setAmount(transferDTO.getAmount());
        transaction.setCreated(new Date());
        transaction.setDestNumber(transferDTO.getDest());
        Transaction savedTransaction = transactionRepository.save(transaction);

        CardType cardType = transferDTO.getSource().split("-")[0].equalsIgnoreCase(CardType.MELLI.getType()) ? CardType.MELLI : CardType.OTHER;
        BankService bankService = bankServices.stream().filter(bs -> bs.getType().equals(cardType)).findFirst().get();
        ResponseEntity result = bankService.transfer(transferDTO);
        if (result.getStatusCode().equals(HttpStatus.OK)) {
            TransactionResultMessage transactionResultMessage = new TransactionResultMessage(savedTransaction.getId(), "Money successfully transfered.", "0937199234");
            mySender.send("transactionQueue", transactionResultMessage);
            transaction.setBankStatus(HttpStatus.OK.value());
        }

        return modelMapper.map(transactionRepository.save(transaction), TransactionDTO.class);
    }

    @Override
    @Transactional
    public List<CardTransactionsDTO> listTransactionsInRange(TransactionFilterDTO transactionFilterDTO) {
        Card card = cardService.findCardById(transactionFilterDTO.getCardId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Card Not Found"));
        //TODO use scrobale result
        StatelessSession statelessSession = ((Session) entityManager.getDelegate()).getSessionFactory().openStatelessSession();
        String queryStr = "SELECT t FROM Transaction t where t.card = :card and t.created >= :startDate and t.created <= :endDate";
        Query query = statelessSession.createQuery(queryStr);
        query.setParameter("card", card);
        query.setParameter("startDate", transactionFilterDTO.getStartDate());
        query.setParameter("endDate", transactionFilterDTO.getEndDate());
        query.setFetchSize(10000);
        query.setReadOnly(true);

        ScrollableResults results = query.scroll(ScrollMode.FORWARD_ONLY);
        List<Transaction> transactionList = new ArrayList<>();
        while (results.next()) {
            transactionList.add((Transaction) results.get()[0]);
        }

        results.close();
        statelessSession.close();

        Map<Long, Map<Integer, Long>> transactionsMap = transactionList.stream()
                .collect(Collectors
                        .groupingBy(t -> t.getCard().getId(), Collectors.groupingBy(Transaction::getBankStatus, Collectors.counting())));
        List<CardTransactionsDTO> cardTransactionsDTOList = new ArrayList<>();
        transactionsMap
                .forEach((id, countMap) -> {
                    CardTransactionsDTO cardTransactionsDTO = new CardTransactionsDTO();
                    cardTransactionsDTO.setCardId(id);
                    countMap.forEach((httpStatus, count) -> {
                        if (httpStatus == HttpStatus.OK.value())
                            cardTransactionsDTO.setSuccessfulTransactionCount(count);
                        else
                            cardTransactionsDTO.setFailedTransactionCount(count);
                    });
                    cardTransactionsDTOList.add(cardTransactionsDTO);
                });
        return cardTransactionsDTOList;
    }

    @Override
    public void setTransactionSmsStatus(SmsResultMessage smsResultMessage) throws PaymentException.NotFound {
        Transaction transaction = transactionRepository.findById(smsResultMessage.getTransactionId()).orElseThrow(() -> new PaymentException.NotFound("transaction not found"));
        transaction.setSmsStatus(smsResultMessage.getStatus());
        transactionRepository.save(transaction);
    }
}
