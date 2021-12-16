package com.sy.web.controller;

import com.sy.web.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author: liangSY
 * @Date: 2021/12/16 10:54
 * @ClassName: controller
 */
@RestController
@RequestMapping("/web/socket")
public class WebSocketController {

    @Autowired
    WebSocketService webSocketService;



    @GetMapping("/send")
    public void send() throws IOException {
        webSocketService.sendMessage("hellow word");
    }
}
