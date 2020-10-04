package com.digipay.payment.entity;

public enum CardType {

    MELLI("6037"),
    OTHER("other");

    private String type;

    CardType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

