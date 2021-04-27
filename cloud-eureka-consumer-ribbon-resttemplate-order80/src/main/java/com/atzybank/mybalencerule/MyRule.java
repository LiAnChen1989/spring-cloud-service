package com.atzybank.mybalencerule;

import com.netflix.loadbalancer.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRule {

    public MyRule(){
        System.out.println("123456789:");
    }
    /**
     * 算法：rest接口第几次请求数 % 服务器集群总数量 = 实际调用服务器位置下标
     * 每次服务重启后rest接口计数从1开始
     *
     * @return 轮询
     */
    @Bean
    public IRule getRoundRobinRule() {

        //默认：轮询
        // return new RoundRobinRule();
        // 随机
        return new RandomRule();
        // 先按照轮询策略获取服务，如果获取服务失败则在指定时间内进行重试，获取可用服务
        // return new RetryRule();
        // 对轮询策略的扩展，相应速度越快的实例选择权重越大，越容易被选择
        // return new WeightedResponseTimeRule();
        // 会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量小的服务
        //return new BestAvailableRule();
        // 先过滤掉故障实例，再选择并发较小的实例
        // return new AvailabilityFilteringRule();
        // 默认规则，复合判断server所在区域的性能和server的可用性选择服务器
        // return new ZoneAvoidanceRule();
    }


}
