package com.example.demo;
import com.example.demo.handlers.HelloWorldHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WebRouter {

    @Bean
    public RouterFunction<ServerResponse> router(HelloWorldHandler helloWorldHandler) {
        return route(GET("/helloWorld"), helloWorldHandler::handle);
    }
}