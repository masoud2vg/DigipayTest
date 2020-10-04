package com.digipay.payment.transaction.api;

import com.digipay.payment.entity.CardType;
import com.digipay.payment.entity.TransferDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BankServiceOtherImpl implements BankService {

    private final OtherFeignService otherFeignService;

    public BankServiceOtherImpl(OtherFeignService otherFeignService) {
        this.otherFeignService = otherFeignService;
    }

    @Override
    public ResponseEntity transfer(TransferDTO transferDTO) {
        return otherFeignService.transfer(transferDTO);
    }

    @Override
    public CardType getType() {
        return CardType.OTHER;
    }
}
