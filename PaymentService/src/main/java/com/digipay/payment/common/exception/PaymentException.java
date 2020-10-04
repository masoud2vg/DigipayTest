package com.digipay.payment.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PaymentException {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NotFound extends BaseException {
        public NotFound(String msg, Object... args) {
            super(msg, args);
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static class DuplicateCard extends BaseException {
        public DuplicateCard(String msg, Object... args) {
            super(msg, args);
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static class Validation extends BaseException {
        public Validation(String msg, Object... args) {
            super(msg, args);
        }
    }
}
