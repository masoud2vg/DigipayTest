package com.digipay.payment.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionFilterDTO {

    private Long cardId;
    private Date startDate;
    private Date endDate;
}

