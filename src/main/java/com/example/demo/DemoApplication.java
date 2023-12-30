package com.example.demo;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import reactor.core.publisher.*;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        Hooks.enableAutomaticContextPropagation();
        SpringApplication.run(DemoApplication.class, args);
    }
}

