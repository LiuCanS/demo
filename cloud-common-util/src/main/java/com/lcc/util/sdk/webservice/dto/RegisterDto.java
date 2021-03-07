package com.lcc.util.sdk.webservice.dto;

import lombok.Data;

/**
 * 注册时需要的内容
 * @author lcc
 */
@Data
public class RegisterDto {

    String province;

    String city;

    String trade;

    String entname;

    String linkman;

    String phone;

    String mobile;

    String email;

    String fax;

    String address;

    String postcode;

    String sign;
}
