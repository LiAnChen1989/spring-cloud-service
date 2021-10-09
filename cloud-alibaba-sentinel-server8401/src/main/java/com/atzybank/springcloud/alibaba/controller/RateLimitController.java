package com.atzybank.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atzybank.springcloud.alibaba.myhandler.CustomerBlockHandler;
import com.atzybank.springcloud.entyties.CommonResult;
import com.atzybank.springcloud.entyties.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @GetMapping("/bySource")
    @SentinelResource(value = "bySource", blockHandler = "handleException")
    public CommonResult bySource() {
        return new CommonResult(200, "按资源名称限流测试ok", new Payment(2020L, "serial01"));
    }

    public CommonResult handleException(BlockException exception) {
        return new CommonResult(444, exception.getClass().getCanonicalName() + "\t 服务不可用！");
    }

    @GetMapping("/byUrl")
    @SentinelResource(value = "byUrl")
    public CommonResult byUrl() {
        return new CommonResult(200, "按url限流测试ok", new Payment(2020L, "serial02"));
    }

    @GetMapping("/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler", blockHandlerClass = CustomerBlockHandler.class, blockHandler = "handlerException")
    public CommonResult customerBlockHandler() {
        return new CommonResult(200, "按客户自定义限流测试ok", new Payment(2020L, "serial03"));
    }
}
