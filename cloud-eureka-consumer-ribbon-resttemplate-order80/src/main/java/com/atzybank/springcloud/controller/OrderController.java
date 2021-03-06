package com.atzybank.springcloud.controller;

import com.atzybank.springcloud.entyties.CommonResult;
import com.atzybank.springcloud.entyties.Payment;
import com.atzybank.springcloud.loadbalance.MyLoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    public static final String PAYMENT_URL = "http://cloud-payment-service";

    @Resource
    RestTemplate restTemplate;
    @Resource
    MyLoadBalancer loadBalancer;
    @Resource
    DiscoveryClient discoveryClient;
    @GetMapping("/consumer/payment/create")
    public CommonResult create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    @GetMapping("/consumer/payment/lb")
    public String getPaymentLb(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() < 0){
            return  null;
        }
        ServiceInstance serviceInstance = loadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();

        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }

    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin(){
        return restTemplate.getForObject("http://localhost:8001/payment/zipkin",String.class);
    }

}
