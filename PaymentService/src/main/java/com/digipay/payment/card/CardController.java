package com.digipay.payment.card;

import com.digipay.payment.common.exception.PaymentException;
import com.digipay.payment.entity.CardInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    /**
     * add new card
     * @param userId user id
     * @param cardInfoDTO card info to be saved {@link CardInfoDTO}
     * @return
     */
    @PostMapping()
    @Async
    public CompletableFuture<CardInfoDTO> addCard(@RequestHeader(value = "userid") Long userId,
                                                  @RequestBody CardInfoDTO cardInfoDTO)
            throws PaymentException.Validation, PaymentException.DuplicateCard {
        return CompletableFuture.completedFuture(cardService.addCard(userId, cardInfoDTO));
    }

    @DeleteMapping("{id}")
    @Async
    public CompletableFuture<ResponseEntity> removeCard(@RequestHeader(value = "userid") Long userId,
                                                        @PathVariable("id") Long cardId) throws Exception {
        return CompletableFuture.completedFuture(cardService.removeCard(userId, cardId));
    }

    /**
     * list All cards of a user
     * @param userId user id, which in production development we should use token to get the user id
     * @return list of {@link CardInfoDTO}
     */
    @GetMapping()
    @Async
    public CompletableFuture<List<CardInfoDTO>> listCards(@RequestHeader(value = "userid") Long userId) {
        return CompletableFuture.completedFuture(cardService.listCards(userId));
    }
}
