package com.wenqy.hsf.service.impl;


import com.alibaba.boot.hsf.annotation.HSFProvider;
import com.wenqy.hsf.service.EchoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@HSFProvider(serviceInterface = EchoService.class, serviceVersion = "1.0.0")
public class EchoServiceImpl implements EchoService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String echo(String str) {

        return str + ",from-server";
    }

}
