package com.atguigu.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    /*
    *OpenFeign日志打印功能
    * (1)Feign提供了日志打印功能，我们可以通过配置来调整日志级别，从而了解Feign中请求的细节，说白了就是对Feign接口调用情况进行监控和输出
    * */
    /**
     * feignClient配置日志级别
     *
     * @return
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        // 请求和响应的头信息,请求和响应的正文及元数据
        return Logger.Level.FULL;
    }
}

