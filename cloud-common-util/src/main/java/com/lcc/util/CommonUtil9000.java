package com.lcc.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 刘灿灿
 */
@SpringBootApplication
public class CommonUtil9000 {
    public static void main(String[] args) {
        SpringApplication.run(CommonUtil9000.class, args);
        System.out.println("llal");
//        http://localhost:8080/swagger-ui.html
        System.out.println("http://localhost:9000/swagger-ui.html");
    }
}