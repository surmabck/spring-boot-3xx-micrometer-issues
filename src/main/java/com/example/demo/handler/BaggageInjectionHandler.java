package com.example.demo.handler;

import io.micrometer.tracing.Tracer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(-80)
public class BaggageInjectionHandler implements WebFilter {
    Tracer tracer;
    public BaggageInjectionHandler(Tracer tracer) {
        this.tracer = tracer;
    }

    private static Logger logger = LogManager.getLogger(BaggageInjectionHandler.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        logger.info("Injecting custom baggage");
        tracer.createBaggageInScope("test1", "value1");
        tracer.createBaggageInScope("test2", "value2");
        return chain.filter(exchange).contextCapture();
    }
}

