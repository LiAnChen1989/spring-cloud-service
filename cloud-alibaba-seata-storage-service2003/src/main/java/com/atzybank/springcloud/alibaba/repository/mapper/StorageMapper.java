package com.atzybank.springcloud.alibaba.repository.mapper;

import com.atzybank.springcloud.alibaba.domain.Storage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface StorageMapper extends BaseMapper<Storage> {

    void updateStorage(@Param("productId") Long productId, @Param("count") Integer count);
}
