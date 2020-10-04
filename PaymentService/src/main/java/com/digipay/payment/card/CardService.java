package com.digipay.payment.card;

import com.digipay.payment.common.exception.PaymentException;
import com.digipay.payment.entity.Card;
import com.digipay.payment.entity.CardInfoDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CardService {

    CardInfoDTO addCard(Long userId, CardInfoDTO cardInfoDTO) throws PaymentException.Validation, PaymentException.DuplicateCard;
    ResponseEntity removeCard(Long userId, Long cardId) throws Exception;
    Optional<Card> findCard(Long userId, String cardNumber);
    Optional<Card> findCardById(Long cardId);
    List<CardInfoDTO> listCards(Long userId);

    boolean validate(CardInfoDTO cardInfoDTO) throws PaymentException.Validation;
}
