package com.atguigu.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class MySelfRule {

    @Resource
   private MySelfRule mySelfRule;

    @Bean
    public IRule myRule(){
        new RoundRobinRule();
        return new RandomRule();
    }
}
