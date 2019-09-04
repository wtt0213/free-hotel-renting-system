package com.example.backend;

import com.alibaba.fastjson.JSONObject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        JSONObject.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
        SpringApplication.run(BackendApplication.class, args);
    }

}
