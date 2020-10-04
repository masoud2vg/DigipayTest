package com.digipay.payment.receiver;

import com.digipay.payment.common.amqp.SmsResultMessage;
import com.digipay.payment.common.exception.PaymentException;

public interface Receiver {

    void handleMessage(SmsResultMessage message) throws PaymentException.NotFound;
}
