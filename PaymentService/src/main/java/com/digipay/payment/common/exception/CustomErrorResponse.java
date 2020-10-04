package com.digipay.payment.common.exception;

import lombok.Data;

import java.util.Date;

@Data
public class CustomErrorResponse {

    String message;
    int status;
    Date timestamp;

    public CustomErrorResponse(String message) {
        this.message = message;
    }
}