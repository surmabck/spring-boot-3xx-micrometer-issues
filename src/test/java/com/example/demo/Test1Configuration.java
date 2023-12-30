package com.example.demo;

import com.example.demo.handlers.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.*;

@TestConfiguration
public class Test1Configuration {

    @Bean
    public ContextData contextData() {
        return new ContextData("contextDataName", Test1.contextDataValue);
    }
}
