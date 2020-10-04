package com.digipay.payment.receiver;

import com.digipay.payment.common.amqp.TransactionResultMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void send(String topic, TransactionResultMessage transactionResultMessage) {
        try {
            String orderJson = objectMapper.writeValueAsString(transactionResultMessage);
            Message message = MessageBuilder
                    .withBody(orderJson.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .build();
            this.rabbitTemplate.convertAndSend(topic, message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        this.rabbitTemplate.convertAndSend(topic, message);
    }
}
