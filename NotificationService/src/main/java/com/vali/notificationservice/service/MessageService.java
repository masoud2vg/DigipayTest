package com.vali.notificationservice.service;

import com.vali.notificationservice.entity.TransactionResultMessage;

public interface MessageService {

    void sendNotification(TransactionResultMessage transactionResultMessage);
}
