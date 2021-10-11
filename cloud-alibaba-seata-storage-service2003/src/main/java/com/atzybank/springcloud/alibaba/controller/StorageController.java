package com.atzybank.springcloud.alibaba.controller;

import com.atzybank.springcloud.alibaba.service.StorageService;
import com.atzybank.springcloud.entyties.CommonResult;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class StorageController {

    @Resource
    StorageService storageService;

    @PostMapping("/storage/decrease")
    public CommonResult decrease(Long productId,Integer count){
        storageService.decrease(productId,count);
        return new CommonResult(200,"扣减库存成功！");
    }
}