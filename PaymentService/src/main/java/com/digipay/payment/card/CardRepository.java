package com.digipay.payment.card;

import com.digipay.payment.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findById(Long id);

    Optional<Card> findCardByUserIdAndNumber(Long userId, String number);

    Optional<Card> findCardByUserIdAndId(Long userId, Long id);

    List<Card> findAllByUserId(Long userId);
}
