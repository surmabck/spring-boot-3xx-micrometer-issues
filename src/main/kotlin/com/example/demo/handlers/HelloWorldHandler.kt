package com.example.demo.handlers

import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

private val logger = KotlinLogging.logger { }

@Component
class HelloWorldHandler {

    fun handle(request: ServerRequest): Mono<ServerResponse> {

        logger.info { "handling request" }
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(fromValue("HelloWorld"))
    }

}