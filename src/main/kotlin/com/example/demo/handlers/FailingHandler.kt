package com.example.demo.handlers

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class FailingHandler(builder: WebClient.Builder) {

    private val webClient = builder
        .baseUrl("https://non-existing")
        .build()


    fun handle(request: ServerRequest): Mono<ServerResponse> =
        Mono.just(request)
            .flatMap {
                webClient.get().retrieve().bodyToMono(String::class.java)
            }
            .flatMap {
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(it))
            }

}
