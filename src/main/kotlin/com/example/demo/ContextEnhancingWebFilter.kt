package com.example.demo

import com.example.demo.handlers.ContextData
import io.micrometer.tracing.Tracer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.util.context.Context


@Configuration
internal class ContextEnhancingConfiguration {
    @Bean
    fun contextEnhancingWebFilter1(
        tracer: Tracer,
        contextData: ContextData
    ) = ContextEnhancingWebFilter(tracer, contextData)

    @Bean
    fun contextEnhancingWebFilter2(
        tracer: Tracer,
    ) = ContextEnhancingWebFilter(tracer, ContextData("someOtherName", "someOtherValue"))
}

internal class ContextEnhancingWebFilter(
    private val tracer: Tracer,
    private val contextData: ContextData
) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> =
        chain.filter(exchange)
            .transformDeferred { mono ->
                Mono.just(contextData)
                    .flatMap {
                        tracer.currentSpan()?.tag(it.name, it.value)

                        val baggageInScope = tracer.createBaggageInScope(it.name, it.value)

                        mono.doFinally {
                            baggageInScope.close()
                        }
                    }

            }
            .contextWrite {
                setContextValue(contextData, it)
            }
}

internal fun setContextValue(
    contextData: ContextData,
    context: Context
): Context {
    return context.put(contextData.name, contextData.value)
}