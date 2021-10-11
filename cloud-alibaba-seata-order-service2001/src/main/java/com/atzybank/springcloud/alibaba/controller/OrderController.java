package com.atzybank.springcloud.alibaba.controller;

import com.atzybank.springcloud.alibaba.domain.Order;
import com.atzybank.springcloud.alibaba.service.OrderService;
import com.atzybank.springcloud.entyties.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    OrderService orderService;

    @GetMapping("/order/create")
    public CommonResult createOrder(Order order) {
        orderService.addOrder(order);
        return new CommonResult(200,"订单创建成功！");
    }
}
