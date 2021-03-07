package com.lcc.util.sdk.webservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 单条短信发送
 * @author lcc
 */
@Data
public class SingleMessageDto extends BaseSendMessageDto{

    @ApiModelProperty(value = "收到短信的手机号")
    @NotEmpty(message = "手机号不能为空")
    private String phoneNumber;

    @ApiModelProperty(value = "短信模板id")
    @NotEmpty(message = "短信模板id不能为空")
    private String msgModelId;

    @ApiModelProperty(value = "参数")
    private String[] params;


    String content;
    /**
     * 发送时间
     */
    String stime = "";
//    @Override
//    public String toString() {
//
//    }

//    @Override
//    public String toString(){
//
//        return "phoneNumber=" +phoneNumber +"&" +"msgModelId=" + msgModelId;
//    }

    @Override
    public String toString() {
        return
                "phoneNumber='" + phoneNumber + '&' +
                "msgModelId='" + msgModelId + '&' +

                "content='" + content + '&' +
                "stime='" + stime ;
    }
}
