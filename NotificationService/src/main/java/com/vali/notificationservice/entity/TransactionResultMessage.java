package com.vali.notificationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResultMessage implements Serializable {
    private Long transactionId;
    private String msg;
    private String target;
}
