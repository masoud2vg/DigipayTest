package com.digipay.payment.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;


@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    public CustomExceptionHandler() {

    }
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<CustomErrorResponse> baseException(BaseException e) {
        log.error(e.getMessage());
        CustomErrorResponse error = new CustomErrorResponse(e.getMessage());
        error.setTimestamp(new Date());
        error.setStatus((HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

