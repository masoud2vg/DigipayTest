package com.digipay.payment.entity;

import lombok.Data;

@Data
public class TransferDTO {

    private String source;
    private String dest;
    private String cvv2;
    private String expDate;
    private String pin;
    private Long amount;
}
