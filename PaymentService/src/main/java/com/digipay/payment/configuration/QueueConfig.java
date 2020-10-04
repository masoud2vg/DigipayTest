package com.digipay.payment.configuration;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QueueConfig {

    private AmqpAdmin amqpAdmin;

    public QueueConfig(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @PostConstruct
    public void createQueues() {
        amqpAdmin.declareQueue(new Queue("smsQueue", true));
        amqpAdmin.declareQueue(new Queue("transactionQueue", true));
    }
}