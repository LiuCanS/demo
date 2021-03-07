package com.lcc.util.sdk.excel;

import lombok.Data;

/**
 * @author lcc
 */
@Data
public class MsgTemplate {

    /**
     * 序号
     */
    private String index;

    /**
     * 签名
     */
    private String signature;

    /**
     * 事件 短信类型
     */
    private String templateType;

    /**
     * 内容
     */
    private String content;

    /**
     * 行业
     */

    private String trade;

    /**
     * 类型
     */
    private String type;

}
