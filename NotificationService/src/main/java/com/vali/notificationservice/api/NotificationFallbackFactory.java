package com.vali.notificationservice.api;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Slf4j
public class NotificationFallbackFactory implements FallbackFactory<SmsService> {
    @Override
    public SmsService create(Throwable throwable) {
        return new NotificationFallback(throwable);
    }
}
