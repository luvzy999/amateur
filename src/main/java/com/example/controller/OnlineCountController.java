package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSessionBindingListener;

@RestController
@RequestMapping("/data")
public class OnlineCountController implements HttpSessionBindingListener {
    public OnlineCountController(){} 
}
