package com.vali.notificationservice.api;

import com.vali.notificationservice.entity.SmsResultMessage;
import com.vali.notificationservice.entity.TransactionResultMessage;
import com.vali.notificationservice.service.Sender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class NotificationFallback implements SmsService {

    Throwable throwable;

    @Autowired
    private Sender sender;

    public NotificationFallback(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public ResponseEntity sendMessage(TransactionResultMessage transactionResultMessage) {
        if (throwable.getMessage() != null) {
            log.error("fallback; reason was: {}, {}", throwable.getMessage(), throwable);
        }

        SmsResultMessage smsResultMessage = new SmsResultMessage();
        smsResultMessage.setTransactionId(transactionResultMessage.getTransactionId());
        smsResultMessage.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        log.error("sms provider failed to send sms for transaction " + transactionResultMessage.getTransactionId());
        sender.send("transactionQueue", smsResultMessage);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
