package com.digipay.payment.entity;

import lombok.Data;

@Data
public class CardInfoDTO {
    private Long id;
    private String number;
    private String cvv2;
    private String expireDate;
}
