package com.digipay.payment;

import com.digipay.payment.card.CardRepository;
import com.digipay.payment.card.CardServiceImpl;
import com.digipay.payment.common.exception.PaymentException;
import com.digipay.payment.entity.Card;
import com.digipay.payment.entity.CardInfoDTO;
import com.digipay.payment.entity.User;
import com.digipay.payment.user.UserRepository;
import com.digipay.payment.user.UserService;
import com.digipay.payment.user.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CardServiceTests {

    @InjectMocks
    private CardServiceImpl cardService;
    @Mock
    private UserService userService;

    @Spy
    @Autowired
    private ModelMapper modelMapper;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    private User user;
    private Card card;
    private CardInfoDTO cardInfoDTO;

    @Before
    public void setup() {

        user = new User();
        user.setId(1L);
        user.setNumber("09371009016");
        user.setUsername("masoud");

        card = new Card();
        card.setId(1L);
        card.setNumber("6037-1010-9918-3065");
        card.setCvv2("343");
        card.setExpireDate("0308");

        cardInfoDTO = modelMapper.map(card, CardInfoDTO.class);
    }


    @Test
    public void addCard() throws PaymentException.Validation, PaymentException.DuplicateCard {
        when(userService.findUser(any())).thenReturn(Optional.of(user));

        when(cardRepository.save(any(Card.class))).thenReturn(card);
        assertThat(cardService.addCard(1L, cardInfoDTO).getId()).isEqualTo(1L);
    }

}
