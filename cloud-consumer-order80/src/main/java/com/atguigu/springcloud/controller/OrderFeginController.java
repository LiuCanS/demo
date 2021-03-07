package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.FeginPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 刘灿灿
 */
@Slf4j
@RestController
public class OrderFeginController {
    @Resource
    FeginPaymentService feginPaymentService;


    @GetMapping(value = "/openfeign/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id)
    {
        System.out.println(id + "******************" );
        return feginPaymentService.getPaymentById(id);
    }

}
