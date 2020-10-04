package com.digipay.payment.receiver;

import com.digipay.payment.common.amqp.SmsResultMessage;
import com.digipay.payment.common.exception.PaymentException;
import com.digipay.payment.transaction.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQReceiver implements Receiver {

    private final TransactionService transactionService;

    public RabbitMQReceiver(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RabbitListener(queues = "smsQueue")
    public void handleMessage(SmsResultMessage message) throws PaymentException.NotFound {
        log.info("Received sms result is: " + message);
        transactionService.setTransactionSmsStatus(message);
    }
}
