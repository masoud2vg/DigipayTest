package com.vali.notificationservice.messageworker;

import com.vali.notificationservice.entity.TransactionResultMessage;

public interface Receiver {

    void handleMessage(TransactionResultMessage message);
}
