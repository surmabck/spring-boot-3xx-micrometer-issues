package com.example.demo.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class HelloWorldHandler {
    private static Logger logger = LogManager.getLogger(HelloWorldHandler.class);

    public Mono<ServerResponse> handle(ServerRequest request) {
        logger.info("handling request");
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(fromValue("HelloWorld"));
    }
}