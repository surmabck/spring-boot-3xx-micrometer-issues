package com.example.demo

import mu.KotlinLogging
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

private val logger = KotlinLogging.logger { }

@Component
@Order(-3)
internal class ResponseLoggingFilter : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        exchange.response.beforeCommit {
            logger.debug { "Incoming request handled" }
            Mono.empty()
        }

        return chain.filter(exchange)
    }
}