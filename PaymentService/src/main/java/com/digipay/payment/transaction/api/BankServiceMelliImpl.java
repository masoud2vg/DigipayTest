package com.digipay.payment.transaction.api;

import com.digipay.payment.entity.CardType;
import com.digipay.payment.entity.TransferDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BankServiceMelliImpl implements BankService {

    private final MelliFeignService melliFeignService;

    public BankServiceMelliImpl(MelliFeignService melliFeignService) {
        this.melliFeignService = melliFeignService;
    }

    @Override
    public ResponseEntity transfer(TransferDTO transferDTO) {
        return melliFeignService.transfer(transferDTO);
    }

    @Override
    public CardType getType() {
        return CardType.MELLI;
    }
}
