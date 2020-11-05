package com.wenqy.hsf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname CheckHealthController
 * @Description 健康检查
 * @Date 2020/8/10 10:21
 * @Created by wenqy
 */
@RestController
public class CheckHealthController {

    @RequestMapping("/health")
    public String health() {
        return "ok";
    }
}
