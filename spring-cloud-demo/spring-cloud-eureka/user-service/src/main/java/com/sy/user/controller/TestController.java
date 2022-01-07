package com.sy.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: liangSY
 * @Date: 2021/11/30 13:53
 * @ClassName: UserController
 */
@RestController
public class TestController {

    @Autowired
    DiscoveryClient client;

    @GetMapping("/index")
    public String index(){
        List<ServiceInstance> instance = client.getInstances("user-service");
        instance.forEach(ins -> {
            System.out.println("Host："+ins.getHost()+", ServiceId："+ins.getServiceId());
        });
        return instance.toString();
    }
}
