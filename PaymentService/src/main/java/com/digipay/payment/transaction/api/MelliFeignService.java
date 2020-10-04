package com.digipay.payment.transaction.api;

import com.digipay.payment.entity.TransferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "firstbankservice", url = "http://j428r.mocklab.io")
public interface MelliFeignService {

    @RequestMapping(method = RequestMethod.POST, value = "/payments/transfer", consumes = "application/json")
    ResponseEntity transfer(TransferDTO transferDTO);
}
