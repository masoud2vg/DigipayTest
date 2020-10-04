package com.digipay.payment.transaction;

import com.digipay.payment.common.exception.PaymentException;
import com.digipay.payment.entity.CardTransactionsDTO;
import com.digipay.payment.entity.TransactionDTO;
import com.digipay.payment.entity.TransactionFilterDTO;
import com.digipay.payment.entity.TransferDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("transfer")
    @Async
    public CompletableFuture<TransactionDTO> transfer(@RequestHeader("userid") Long userId, @RequestBody TransferDTO transferDTO) throws PaymentException.NotFound {
        return CompletableFuture.completedFuture(transactionService.transfer(userId,transferDTO));
    }

    @PostMapping()
    @Async
    public CompletableFuture<List<CardTransactionsDTO>> listTransactions(@RequestBody TransactionFilterDTO transactionFilterDTO){
        return CompletableFuture.completedFuture(transactionService.listTransactionsInRange(transactionFilterDTO));
    }
}
