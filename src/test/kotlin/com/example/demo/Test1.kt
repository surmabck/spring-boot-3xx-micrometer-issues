package com.example.demo

import com.example.demo.handlers.ContextData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.UseMainMethod.ALWAYS
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
private val contextDataValue = "value1"

@AutoConfigureObservability
@AutoConfigureWebTestClient
@SpringBootTest(useMainMethod = ALWAYS, classes = [ContextDataProvider::class])
@ExtendWith(OutputCaptureExtension::class)
private class DemoApplicationTest(@Autowired private val testClient: WebTestClient) {
    private val contextDataNameInMdcLogPattern = "\\[contextDataName=$contextDataValue\\]"
    private val contextDataNameInPlainLogPattern = "contextDataName: $contextDataValue"

    @Test
    fun `should return 200 ok and log message with traceId`(output: CapturedOutput) {
        testClient
            .get()
            .uri("/helloWorld")
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .expectStatus()
            .is2xxSuccessful
        assertThat(output).containsPattern(contextDataNameInMdcLogPattern);
    }

    @Test
    fun `should return 200 ok and log message with traceId2`(output: CapturedOutput) {
        testClient
            .get()
            .uri("/helloWorld")
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .expectStatus()
            .is2xxSuccessful
        assertThat(output).containsPattern(contextDataNameInPlainLogPattern);
    }

    @Test
    fun `regex should match contextDataName in mdc`() {
        val sampleOutput = "[occurredAt: 2023-12-29 15:39:54,776] [traceId=658eda3acf8710f4c1d38348b33ee35b] [span=c1d38348b33ee35b] [contextDataName=$contextDataValue] severity=INFO   [thread=parallel-2] logger=HelloWorldHandler - Should be logged with the value in the MDC context\n"
        assertThat(sampleOutput).containsPattern(contextDataNameInMdcLogPattern);
    }

    @Test
    fun `regex should match contextDataName in plain`() {
        val sampleOutput = "[occurredAt: 2023-12-29 15:39:54,776] [traceId=658eda3acf8710f4c1d38348b33ee35b] [span=c1d38348b33ee35b] [contextDataName=$contextDataValue] severity=INFO   [thread=parallel-2] logger=HelloWorldHandler - Should be logged with a value logged in plain contextDataName: $contextDataValue\n"
        assertThat(sampleOutput).containsPattern(contextDataNameInPlainLogPattern);
    }
}

@TestConfiguration
private class ContextDataProvider {

    @Bean
    fun id(): ContextData = ContextData("contextDataName", contextDataValue )
}