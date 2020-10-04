package com.vali.notificationservice.messageworker;

import com.vali.notificationservice.entity.TransactionResultMessage;
import com.vali.notificationservice.service.MessageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQReceiver implements Receiver {

    private final MessageService messageService;

    public RabbitMQReceiver(MessageService messageService) {
        this.messageService = messageService;
    }

    @RabbitListener(queues = "transactionQueue")
    public void handleMessage(TransactionResultMessage message) {
        messageService.sendNotification(message);
    }
}
