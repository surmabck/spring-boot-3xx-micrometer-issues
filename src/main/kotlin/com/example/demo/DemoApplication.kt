package com.example.demo

import com.example.demo.handlers.HelloWorldHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Hooks


@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args) {
        Hooks.enableAutomaticContextPropagation();
    }
}

@Configuration
internal class WebRouter {

    @Bean
    fun router(helloWorldHandler: HelloWorldHandler): RouterFunction<ServerResponse> =
        router {
            GET("/helloWorld", helloWorldHandler::handle)
        }

}

