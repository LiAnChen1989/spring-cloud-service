package com.atzybank.springcloud.service.impl;

import com.atzybank.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

@Component
public class PaymentHystrixServiceFallbackImpl implements PaymentHystrixService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "对方服务器宕机！";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "对方服务器宕机！";
    }
}
