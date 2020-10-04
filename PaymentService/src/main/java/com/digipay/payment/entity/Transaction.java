package com.digipay.payment.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction", indexes = {@Index(name = "transaction_card_index", columnList = "card_id, created")})
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    @GeneratedValue(generator="system-uuid")
//    @GenericGenerator(name="system-uuid", strategy = "uuid")
//    @Column(name = "id", updatable = false, nullable = false)
//    private UUID id;
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;

    private String destNumber;
    private Long amount;
    private int bankStatus;
    private int smsStatus;
    private Date created;
}
