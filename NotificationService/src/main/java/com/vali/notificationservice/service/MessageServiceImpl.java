package com.vali.notificationservice.service;

import com.vali.notificationservice.api.SmsService;
import com.vali.notificationservice.entity.TransactionResultMessage;
import com.vali.notificationservice.entity.SmsResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final SmsService smsService;
    private final Sender sender;

    @Autowired
    public MessageServiceImpl(SmsService smsService, Sender sender) {
        this.smsService = smsService;
        this.sender = sender;
    }

    @Override
    public void sendNotification(TransactionResultMessage transactionResultMessage) {
        ResponseEntity responseEntity = smsService.sendMessage(transactionResultMessage);

        if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            SmsResultMessage smsResultMessage = new SmsResultMessage();
            smsResultMessage.setTransactionId(transactionResultMessage.getTransactionId());
            smsResultMessage.setStatus(responseEntity.getStatusCode().value());
            log.info("sms provider sent sms for transaction " + transactionResultMessage.getTransactionId());
            sender.send("smsQueue", smsResultMessage);
        }
    }
}
