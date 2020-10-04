package com.vali.notificationservice.service;

import com.vali.notificationservice.entity.SmsResultMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String topic, SmsResultMessage smsResultMessage) {
        this.rabbitTemplate.convertAndSend(topic, smsResultMessage);
    }
}
