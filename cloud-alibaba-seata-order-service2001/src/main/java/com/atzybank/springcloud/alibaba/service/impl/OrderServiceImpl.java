package com.atzybank.springcloud.alibaba.service.impl;

import com.atzybank.springcloud.alibaba.domain.Order;
import com.atzybank.springcloud.alibaba.repository.mapper.OrderMapper;
import com.atzybank.springcloud.alibaba.service.AccountService;
import com.atzybank.springcloud.alibaba.service.OrderService;
import com.atzybank.springcloud.alibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderMapper orderMapper;
    @Resource
    AccountService accountService;
    @Resource
    StorageService storageService;

    @Override
    @GlobalTransactional(name = "fsp", rollbackFor = Exception.class)
    public void addOrder(Order order) {
        log.info("---->开始新建订单");
        orderMapper.insert(order);

        log.info("---->订单微服务开始调用库存，做扣减Count");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("---->订单微服务开始调用库存，做扣减end");

        log.info("---->订单微服务开始调用账户，做扣减Money");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("---->订单微服务开始调用账户，做扣减end");

        //修改订单状态，从0到1，1表示已完成
        Order order1 = new Order();
        order1.setStatus(1);
        log.info("---->修改订单状态开始");
        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", order.getUserId());
        orderMapper.updateByExampleSelective(order1, example);
        log.info("---->修改订单状态结束");

        log.info("---->下订单结束！");
    }
}
