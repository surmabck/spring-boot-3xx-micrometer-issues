package com.example.demo;

import com.example.demo.handlers.*;
import io.micrometer.tracing.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextEnhancingConfiguration {
    @Bean
    public ContextEnhancingWebFilter contextEnhancingWebFilter1(
            Tracer tracer,
            ContextData contextData
    ) {
        return new ContextEnhancingWebFilter(tracer, contextData);
    }

    @Bean
    public ContextEnhancingWebFilter contextEnhancingWebFilter2(
            Tracer tracer
    ) {
        return new ContextEnhancingWebFilter(tracer, new ContextData("someOtherName", "someOtherValue"));
    }
}

