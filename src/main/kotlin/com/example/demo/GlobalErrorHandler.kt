package com.example.demo

import mu.KotlinLogging
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Mono

private val logger = KotlinLogging.logger { }

@Order(-2)
@Component
class GlobalErrorHandler : WebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        logger.error { "Error has occured '${ex.message}'" }
        ex.printStackTrace()
        exchange.response.statusCode = HttpStatusCode.valueOf(500)
        return exchange.response.setComplete()
    }
}
