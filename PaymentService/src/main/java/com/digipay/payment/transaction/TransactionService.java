package com.digipay.payment.transaction;

import com.digipay.payment.common.amqp.SmsResultMessage;
import com.digipay.payment.common.exception.PaymentException;
import com.digipay.payment.entity.CardTransactionsDTO;
import com.digipay.payment.entity.TransferDTO;
import com.digipay.payment.entity.TransactionDTO;
import com.digipay.payment.entity.TransactionFilterDTO;

import java.util.List;

public interface TransactionService {

    TransactionDTO transfer(Long userId, TransferDTO transferDTO) throws PaymentException.NotFound;

    List<CardTransactionsDTO> listTransactionsInRange(TransactionFilterDTO transactionFilterDTO);

    void setTransactionSmsStatus(SmsResultMessage smsResultMessage) throws PaymentException.NotFound;
}
