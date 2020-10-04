package com.digipay.payment.transaction.api;

import com.digipay.payment.entity.CardType;
import com.digipay.payment.entity.TransferDTO;
import org.springframework.http.ResponseEntity;

public interface BankService {

    ResponseEntity transfer(TransferDTO transferDTO);

    CardType getType();
}
