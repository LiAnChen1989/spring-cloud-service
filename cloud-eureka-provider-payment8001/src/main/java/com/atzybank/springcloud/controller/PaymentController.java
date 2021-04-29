package com.atzybank.springcloud.controller;

import com.atzybank.springcloud.entyties.CommonResult;
import com.atzybank.springcloud.entyties.Payment;
import com.atzybank.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    PaymentService paymentService;
    @Value("${server.port}")
    String serverPort;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info(String.valueOf(result));
        if (result > 0) {
            return new CommonResult(1, "插入成功,serverPort:"+serverPort, result);
        } else {
            return new CommonResult(0, "插入失败");
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info(String.valueOf(payment));
        if (payment != null) {
            return new CommonResult(1, "查询成功,serverPort:" + serverPort, payment);
        } else {
            return new CommonResult(0, "查询失败");
        }
    }

    @GetMapping("/payment/lb")
    public String getPaymentLb(){
        return serverPort;
    }

    @GetMapping("/payment/zipkin")
    public String paymentZipkin(){
        return "I am zipkin!";
    }
}
