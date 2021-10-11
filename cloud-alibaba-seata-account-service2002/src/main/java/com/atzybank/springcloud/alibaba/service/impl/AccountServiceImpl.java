package com.atzybank.springcloud.alibaba.service.impl;

import com.atzybank.springcloud.alibaba.repository.mapper.AccountMapper;
import com.atzybank.springcloud.alibaba.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Resource
    AccountMapper accountMapper;

    @Override
    public void decrease(Long userId, BigDecimal money) {
        log.info("----> 更新账户开始");
        accountMapper.updateAccount(userId, money);
        log.info("----> 更新账户结束");
    }
}
