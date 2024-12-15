package com.example.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSessionBindingListener;

@Api(tags = "在线用户统计接口")
@RestController
@RequestMapping("/data")
public class OnlineCountController implements HttpSessionBindingListener {
    public OnlineCountController(){} 
}
