package com.atzybank.springcloud.alibaba.service.impl;

import com.atzybank.springcloud.alibaba.domain.Storage;
import com.atzybank.springcloud.alibaba.repository.mapper.StorageMapper;
import com.atzybank.springcloud.alibaba.service.StorageService;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements StorageService {

    @Resource
    StorageMapper storageMapper;

    @Override
    public void decrease(Long productId, Integer count) {
        log.info("----> 扣减库存开始！");
        storageMapper.updateStorage(productId, count);
        log.info("----> 扣减库存结束！");
    }
}
