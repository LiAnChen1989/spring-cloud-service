package com.atzybank.springcloud.service;

import com.atzybank.springcloud.entyties.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {

    int create(Payment payment);

    Payment getPaymentById(@Param("id")Long id);
}
