package com.atzybank.springcloud.alibaba;

import com.atzybank.springcloud.alibaba.service.StorageService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SeataStorageService2003AppTests {

    @Resource
    StorageService storageService;

    @SneakyThrows
    @Test
    public void testDecrease(){
        storageService.decrease(1L,10);
    }

}
