package com.vali.notificationservice.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SmsResultMessage implements Serializable {
    private Long transactionId;
    private int status;
}
