package com.atzybank.springcloud.alibaba.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atzybank.springcloud.entyties.CommonResult;
import com.atzybank.springcloud.entyties.Payment;

public class CustomerBlockHandler {

    public static CommonResult handlerException(BlockException exception){
        return new CommonResult(444,"按客户global自定义限流测试ok-1",new Payment(2020L,"serial03"));
    }

    public static CommonResult handlerException2(BlockException exception){
        return new CommonResult(444,"按客户global自定义限流测试ok",new Payment(2020L,"serial03"));
    }
}
