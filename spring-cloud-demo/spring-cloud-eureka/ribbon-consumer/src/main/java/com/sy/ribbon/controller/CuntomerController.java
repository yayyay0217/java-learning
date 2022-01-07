package com.sy.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: liangSY
 * @Date: 2021/12/17 11:49
 * @ClassName: CuntomerController
 */
@RestController
public class CuntomerController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping
    public String test(){
       return restTemplate.getForEntity("http://user-service/index",String.class).getBody();
    }
}
