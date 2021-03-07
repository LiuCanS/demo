package com.lcc.util.sdk.webservice.dto;

import lombok.Data;

import java.util.List;

/**
 * 群发个性化短信 一个手机号码 一个短信内容 一个发送时间
 * @author lcc
 */
@Data
public class BatchPersonalMessageDto extends BaseSendMessageDto{
    List<SingleMessageDto> list;
}
