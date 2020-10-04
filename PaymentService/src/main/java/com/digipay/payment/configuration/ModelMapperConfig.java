package com.digipay.payment.configuration;

import com.digipay.payment.entity.Card;
import com.digipay.payment.entity.CardInfoDTO;
import com.digipay.payment.entity.Transaction;
import com.digipay.payment.entity.TransactionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(CardInfoDTO.class, Card.class).addMappings(mapper -> {
                    mapper.skip(Card::setId);
                }
        );

        modelMapper.createTypeMap(Transaction.class, TransactionDTO.class).addMappings(mapper -> {
           mapper.map(t -> t.getCard().getNumber(), TransactionDTO::setSourceNumber);
            mapper.map(Transaction::getDestNumber, TransactionDTO::setSourceNumber);
        });

        return modelMapper;
    }
}
