package com.atzybank.springcloud.alibaba.repository.mapper;

import com.atzybank.springcloud.alibaba.domain.Account;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;

@org.apache.ibatis.annotations.Mapper
public interface AccountMapper extends Mapper<Account> {
    void updateAccount(@Param("userId") Long userId, @Param("money") BigDecimal money);
}
