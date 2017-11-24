package com.jy.study.spring.security.service.impl;

import com.jy.study.spring.security.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String greeting(String name) {
        return "hello " + name;
    }
}
