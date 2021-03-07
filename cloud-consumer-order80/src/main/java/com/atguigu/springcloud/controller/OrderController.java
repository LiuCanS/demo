package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author 刘灿灿
 */
@Slf4j
@RestController
public class OrderController {

//    public  static  final  String PAYMENT_URL = "http://localhost:8001";
    //为服务名称
    public  static  final  String PAYMENT_URL = "http://cloud-provider-payment8001";

    @Resource
    private RestTemplate restTemplate;
    @Resource
    PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/consumer/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
//        int result = paymentService.create(payment);
//        log.info("*****插入结果："+result);
//
//
//        if(result > 0) {
//            return new CommonResult(200,"插入数据库成功,serverPort: "+serverPort,result);
//        }else{
//            return new CommonResult(444,"插入数据库失败",null);
//        }

        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id)
    {

//        Payment payment = paymentService.getPaymentById(id);
//        System.out.println("ddddddd");
//        if(payment != null) {
//            return new CommonResult(200,"查询成功,serverPort:  "+serverPort,payment);
//        }else{
//            return new CommonResult(444,"没有对应记录,查询ID: "+id,null);
//        }
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

}
