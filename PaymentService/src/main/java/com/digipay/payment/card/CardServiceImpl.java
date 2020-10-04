package com.digipay.payment.card;

import com.digipay.payment.common.exception.PaymentException;
import com.digipay.payment.entity.Card;
import com.digipay.payment.entity.CardInfoDTO;
import com.digipay.payment.entity.User;
import com.digipay.payment.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public CardServiceImpl(CardRepository cardRepository, UserService userService, ModelMapper modelMapper) {
        this.cardRepository = cardRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CardInfoDTO addCard(Long userId, CardInfoDTO cardInfoDTO) throws PaymentException.Validation, PaymentException.DuplicateCard {
        validate(cardInfoDTO);
        User user = userService.findUser(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Optional<Card> cardByUserIdAndNumber = cardRepository.findCardByUserIdAndNumber(userId, cardInfoDTO.getNumber());
        if (cardByUserIdAndNumber.isPresent())
            throw new PaymentException.DuplicateCard("this card already existed in database");

        Card card = modelMapper.map(cardInfoDTO, Card.class);
        card.setUser(user);
        Card savedCard = cardRepository.save(card);

        return modelMapper.map(savedCard, CardInfoDTO.class);
    }


    @Override
    public ResponseEntity removeCard(Long userId, Long cardId) throws PaymentException.NotFound {
        Card card = cardRepository.findCardByUserIdAndId(userId, cardId).orElseThrow(() -> new PaymentException.NotFound("The card doesn't exist for the user"));
        cardRepository.delete(card);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Card removed");
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Override
    public Optional<Card> findCard(Long userId, String cardNumber) {
        return cardRepository.findCardByUserIdAndNumber(userId, cardNumber);
    }

    @Override
    public Optional<Card> findCardById(Long cardId) {
        return cardRepository.findById(cardId);
    }

    @Override
    public List<CardInfoDTO> listCards(Long userId) {
        return cardRepository.findAllByUserId(userId)
                .stream()
                .map(card -> modelMapper.map(card, CardInfoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean validate(CardInfoDTO cardInfoDTO) throws PaymentException.Validation {
        if (cardInfoDTO.getNumber().split("-").length != 4)
            throw new PaymentException.Validation("Card info is not correct!");
        return true;
    }
}
