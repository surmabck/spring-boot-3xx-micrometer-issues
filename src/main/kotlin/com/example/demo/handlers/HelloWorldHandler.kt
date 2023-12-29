package com.example.demo.handlers

import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class HelloWorldHandler {


    private val logger = KotlinLogging.logger {}

    fun handle(request: ServerRequest): Mono<ServerResponse> {
        logger.info { "Should be logged with the value in the MDC context" }
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
            .body(fromValue("HelloWorld"))
            .transformDeferredContextual { mono, ctx ->
                logger.info { "Should be logged with a value logged in plain contextDataName: ${ctx.getOrDefault("contextDataName", "default")}" }
                mono
            }
    }

}

data class ContextData(val name: String, val value: String)