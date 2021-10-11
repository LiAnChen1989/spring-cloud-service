package com.atzybank.springcloud.alibaba;

import com.atzybank.springcloud.alibaba.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;

@SpringBootTest
public class AppTest {

    @Resource
    AccountService accountService;

    @Test
    public void test(){
        accountService.decrease(1L,new BigDecimal("10"));
    }
}
