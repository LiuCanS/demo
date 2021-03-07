package com.lcc.util.sdk.webservice.service;

import com.lcc.util.sdk.webservice.BankSmsCons;
import com.lcc.util.sdk.webservice.SmsClient;
import com.lcc.util.sdk.webservice.dto.BatchMessageDto;
import com.lcc.util.sdk.webservice.dto.BatchPersonalMessageDto;
import com.lcc.util.sdk.webservice.dto.SingleMessageDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;


/**
 * @author elyar
 * @date 2020/8/27 19:26
 * @description
 */
@Slf4j
@AllArgsConstructor
@Service
public class BusinessSendMessageImpl  {


    public void sendSingleMessage(SingleMessageDto dto) throws IOException {
        log.info("发送单条短信：" + dto);

        String content = getContent(dto.getContent(), dto.getParams());
        SmsClient smsClient = new SmsClient(dto.getSn(),dto.getPassword());
        String message = smsClient.sendSingleMessage(dto.getPhoneNumber(), content, "", dto.getStime(), "", "");
    }

    @Transactional(rollbackFor = Exception.class)
    public void sendBatchMessage(BatchMessageDto dto) throws IOException {
        //模板信息
        String content = getContent(dto.getContent(), dto.getParams());
        SmsClient smsClient = new SmsClient(dto.getSn(),dto.getPassword());
        //发送信息
        String message = smsClient.sendBatchMessage(dto.getPhoneNumber(), content, "", dto.getStime(), "", "");
    }

    /**
     * 发送个性化短信  相同类型的短信内容 催收 通知
     * @param dto
     * @throws IOException
     */
    public void sendPersonalMessage(BatchPersonalMessageDto dto) throws IOException {
        List<SingleMessageDto> list = dto.getList();
        if (list != null && list.size() != 0) {
            SmsClient smsClient = new SmsClient(dto.getSn(), dto.getPassword());
            int size = list.size();
            String[] mobile = new String[size];
            String[] content = new String[size];
            String[] stime = new String[size];
            int index = 0;
            for (SingleMessageDto single : list) {
                content[index] = single.getContent();
                mobile[index] = single.getPhoneNumber();
                stime[index] = single.getStime();
                index++;
            }
            String resultMes = smsClient.sendBatchPersonalMessage(mobile, content, "", stime, "", "");
            String[] split = resultMes.split(",");
            String realResult = split[0];
        }
    }



    /**
     * 拼接单条短信的发送内容
     * 获取占位符个数的方法 目前只支持顺序排序 且 占位符类型为{1 。。。}
     * @param sourceContent
     * @param params
     * @return
     */
    private String getContent(String sourceContent,String[] params){
        if(params != null && params.length > 0){
            for (int i = 1; i <= params.length ; i++) {
                sourceContent = sourceContent.replace("{" + i + "}", params[i-1]);
            }
        }
        return sourceContent;
    }

    /**
     * 获取想应发送端
     * @param type
     * @return
     */
    private SmsClient getSmsClient(String type){
        SmsClient smsClient = null;
        switch (type) {
            case "1":
                smsClient = new SmsClient(BankSmsCons.SN_1,BankSmsCons.SN_1_PASSWORD);
                break;
            case "2":
                smsClient = new SmsClient(BankSmsCons.SN_2,BankSmsCons.SN_2_PASSWORD);
                break;
            case "3":
                smsClient = new SmsClient(BankSmsCons.SN_3,BankSmsCons.SN_3_PASSWORD);
                break;
            default:
                break;
        }
        return smsClient;
    }


}
