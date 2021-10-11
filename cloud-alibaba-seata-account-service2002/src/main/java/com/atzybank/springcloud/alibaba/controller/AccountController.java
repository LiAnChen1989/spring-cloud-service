package com.atzybank.springcloud.alibaba.controller;

import com.atzybank.springcloud.entyties.CommonResult;
import com.atzybank.springcloud.alibaba.service.AccountService;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@RestController
public class AccountController {
    @Resource
    AccountService accountService;

    @SneakyThrows
    @PostMapping("/account/decrease")
    public CommonResult decrease(Long userId, BigDecimal money){
        TimeUnit.SECONDS.sleep(20);
        accountService.decrease(userId,money);
        return new CommonResult(200,"更新账户成功！");
    }
}
