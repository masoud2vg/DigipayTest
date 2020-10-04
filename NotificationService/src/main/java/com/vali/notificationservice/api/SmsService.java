package com.vali.notificationservice.api;

import com.vali.notificationservice.entity.TransactionResultMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "smsservice", url = "http://j428r.mocklab.io", fallbackFactory = NotificationFallbackFactory.class)
public interface SmsService {

    @RequestMapping(method = RequestMethod.POST, value = "/messages/send-sms", consumes = "application/json")
    ResponseEntity sendMessage(@RequestBody TransactionResultMessage transactionResultMessage);
}
