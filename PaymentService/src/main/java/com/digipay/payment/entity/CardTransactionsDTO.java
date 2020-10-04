package com.digipay.payment.entity;

import lombok.Data;

@Data
public class CardTransactionsDTO {
    private Long cardId;
    private Long successfulTransactionCount = 0L;
    private Long failedTransactionCount = 0L;

}
