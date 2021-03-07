package com.lcc.util.sdk.webservice.dto;

import lombok.Data;

/**
 * @author lcc
 */
@Data
public class SendMessage {

    String msgModelId;


    String systemCode;

    /**
     * 接收号码
     */
    String phoneNumber;

    /**
     * 发送内容 已经拼接过
     */
    String content;

    /**
     * 发送时间
     */
    String stime = "";
}
