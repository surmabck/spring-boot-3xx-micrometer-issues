package com.example.demo.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class HelloWorldHandler {
    private Logger logger = LoggerFactory.getLogger(HelloWorldHandler.class);

    public Mono<ServerResponse> handle(ServerRequest request) {
        logger.info("Should be logged with the value in the MDC context");
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .bodyValue("HelloWorld")
                .transformDeferredContextual((mono, ctx) -> {
                    logger.info("Should be logged with a value logged in plain contextDataName: " + ctx.getOrDefault("contextDataName", "default"));
                    return mono;
                });
    }
}

