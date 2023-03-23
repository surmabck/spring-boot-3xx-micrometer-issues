package com.example.demo

import io.micrometer.tracing.Tracer
import io.micrometer.tracing.propagation.Propagator
import mu.KotlinLogging
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.context.Context

private val logger = KotlinLogging.logger { }

@Component
@Order(-80)
internal class BaggageInjectingHandler(
    private val tracer: Tracer,
) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        logger.info { "Injecting custom baggage" }
        tracer.createBaggageInScope("test1", "value1")
        tracer.createBaggageInScope("test2", "value2")
        return chain.filter(exchange)
    }
}
