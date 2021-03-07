package com.lcc.util.test.controller;

import com.lcc.util.sdk.webservice.dto.SingleMessageDto;
import com.lcc.util.sdk.webservice.service.BusinessSendMessageImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lcc
 */
@Api(tags = {"短信发送"})
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {


    @Autowired
    BusinessSendMessageImpl businessSendMessage;


    private RestTemplate  restTemplate = new RestTemplate();

    @ApiOperation("单发短信")
    @GetMapping("/send/single")
    /**
     * 普通群发短信
     * 一个 多个手机号码   一个内容  一个时间
     */
    public void sendSingleMessage(String phoneNumber,String msgModelId) throws IOException {
        SingleMessageDto dto = new SingleMessageDto();
        dto.setPhoneNumber(phoneNumber);
        dto.setMsgModelId(msgModelId);
        Map params = new HashMap<>();
        params.put("phoneNumber",phoneNumber);
        params.put("msgModelId",msgModelId);
        SingleMessageDto forObject = restTemplate.getForObject("http://localhost:9099/test/send/t1?", SingleMessageDto.class, params);


        System.out.println("***************");
        String s1 = "http://localhost:9099/test/send/t1?" + "phoneNumber=" + phoneNumber + "&" + "msgModelId=" + msgModelId;
        System.out.println(s1);
        restTemplate.getForObject(s1, SingleMessageDto.class);

        System.out.println("***************");
        restTemplate.getForObject("http://localhost:9099/test/send/t2?"+"phoneNumber="+ phoneNumber+"&"+"msgModelId="+msgModelId, SingleMessageDto.class);
        System.out.println("*******t1********");
        System.out.println("***************");
        String s = "http://localhost:9099/test/send/t2?" + dto.toString();
        System.out.println(s);
        restTemplate.getForObject(s,SingleMessageDto.class);

//        restTemplate.getForObject("http://localhost:9000/test/send/t1?{1}", SingleMessageDto.class,params);
//
//        System.out.println("***************");
//        restTemplate.getForObject("http://localhost:9000/test/send/t1?{1}", SingleMessageDto.class,dto);
//        System.out.println("*******t2********");
//        System.out.println("***************");
//        restTemplate.getForObject("http://localhost:9000/test/send/t2?{1}", SingleMessageDto.class,params);
//
//        System.out.println("***************");
//        restTemplate.getForObject("http://localhost:9000/test/send/t2?{1}", SingleMessageDto.class,dto);
    }

    @ApiOperation("单发短信")
    @GetMapping("/send/single2")
    public void send2(String phoneNumber,String msgModelId) throws IOException {
        SingleMessageDto dto = new SingleMessageDto();
        dto.setPhoneNumber(phoneNumber);
        dto.setMsgModelId(msgModelId);

        Map params = new HashMap<>();
        params.put("phoneNumber",phoneNumber);
        params.put("msgModelId",msgModelId);
        SingleMessageDto forObject = restTemplate.getForObject("http://localhost:9000/test/send/t2?", SingleMessageDto.class, params);
        System.out.println(forObject);
    }

    @ApiOperation("单发短信")
    @GetMapping("/send/t1")
    public void get(String phoneNumber,String msgModelId) throws IOException {
        System.out.println("t1" + phoneNumber + "********" + msgModelId);
    }

    @ApiOperation("单发短信")
    @GetMapping("/send/t2")
    public void get(SingleMessageDto dto) throws IOException {
        System.out.println(dto.getMsgModelId() + "*********" + dto.getPhoneNumber());
        System.out.println("t2" + dto);
    }
}
