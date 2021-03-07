package com.lcc.util.test.controller;

import com.lcc.util.sdk.webservice.service.BusinessSendMessageImpl;
import com.lcc.util.sdk.webservice.BankSmsCons;
import com.lcc.util.sdk.webservice.SmsClient;
import com.lcc.util.sdk.webservice.dto.BatchMessageDto;
import com.lcc.util.sdk.webservice.dto.BatchPersonalMessageDto;
import com.lcc.util.sdk.webservice.dto.RegisterDto;
import com.lcc.util.sdk.webservice.dto.SingleMessageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lcc
 */
@Api(tags = {"短信发送"})
@Slf4j
@RestController
@RequestMapping("/sdk")
public class SdkController {

    @Autowired
    BusinessSendMessageImpl businessSendMessage;

    @ApiOperation("单发短信")
    @PostMapping("/send/single")
    /**
     * 普通群发短信
     * 一个 多个手机号码   一个内容  一个时间
     */
    public void sendSingleMessage(@Validated @RequestBody SingleMessageDto dto) throws IOException {
        businessSendMessage.sendSingleMessage(dto);
    }



    @ApiOperation("普通群发短信")
    @PostMapping("/send/batch")
    /**
     * 普通群发短信
     * 一个 多个手机号码   一个内容  一个时间
     */
    public void sendBatchMessage(@Validated @RequestBody BatchMessageDto dto) throws IOException {
        businessSendMessage.sendBatchMessage(dto);
    }




    @ApiOperation("发送个性短信")
    @PostMapping("/send/personal")
    /**
     * 一个 或者 多个手机号码   0 1 多个内容  0 1 多个时间
     */
    public void sendPersonalMessage(@Validated @RequestBody BatchPersonalMessageDto dto) throws IOException {
        businessSendMessage.sendPersonalMessage(dto);
    }


    @ApiOperation("注册")
    @PostMapping("/register")

    /**
     * 简单批量注册
     * @param dto
     * @return
     * @throws IOException
     */
    public void register(@Validated @RequestBody RegisterDto dto) throws IOException {
        List<SmsClient> clientList = getClientList();
        List<String> resultList = new ArrayList<>();
        for (SmsClient smsClient: clientList) {
            String register = smsClient.register(dto.getProvince(), dto.getCity(), dto.getTrade(), dto.getEntname(), dto.getLinkman(),
                    dto.getPhone(), dto.getMobile(),dto.getEmail(),dto.getFax(), dto.getAddress(), dto.getPostcode(), dto.getSign());
            resultList.add(register);
        }
    }


    @ApiOperation("注销")
    @PostMapping("/unRegister")
    /**
     * 简单批量注销
     */
    public void unRegister() throws IOException {

        List<SmsClient> clientList = getClientList();
        List<String> resultList = new ArrayList<>();
        for (SmsClient smsClient: clientList) {
            String unRegister = smsClient.unRegister();
            resultList.add(unRegister);
        }
    }

    public List<SmsClient> getClientList() {
        List<SmsClient> list = new ArrayList<>();
        SmsClient smsClient = new SmsClient(BankSmsCons.SN_1, BankSmsCons.SN_1_PASSWORD);
        list.add(smsClient);
        smsClient = new SmsClient(BankSmsCons.SN_2, BankSmsCons.SN_2_PASSWORD);
        list.add(smsClient);
        smsClient = new SmsClient(BankSmsCons.SN_3, BankSmsCons.SN_3_PASSWORD);
        list.add(smsClient);
        return list;
    }
}
