package com.atzybank.springcloud.alibaba.service;

import com.atzybank.springcloud.alibaba.domain.Storage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface StorageService extends IService<Storage> {
    void decrease(Long productId,Integer account);
}
