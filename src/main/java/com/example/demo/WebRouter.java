package com.example.demo;

import com.example.demo.handler.HelloWorldHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
class WebRouter {

    @Bean
    RouterFunction<ServerResponse> router(HelloWorldHandler handler) {
        return route(GET("/helloWorld"), req -> handler.handle(req));
    }

}
