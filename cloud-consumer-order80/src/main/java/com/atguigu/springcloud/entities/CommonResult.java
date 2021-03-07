package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult <T>{

    int code;

    String message;

    T data;

    public CommonResult(int code, String message) {
        this(code,message,null);
    }

}