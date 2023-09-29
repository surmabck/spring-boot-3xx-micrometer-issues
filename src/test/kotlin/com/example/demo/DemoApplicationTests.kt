package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@AutoConfigureObservability
@AutoConfigureWebTestClient
@SpringBootTest
@ExtendWith(OutputCaptureExtension::class)
private class DemoApplicationTest(@Autowired private val testClient: WebTestClient) {
    private val traceIdPattern = "\\[traceId=[a-z0-9]{32}\\]"
    @Test
    fun `should return 200 ok and log message with traceId`(output: CapturedOutput) {
        testClient
            .get()
            .uri("/helloWorld")
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .expectStatus()
            .is2xxSuccessful
        assertThat(output).containsPattern(traceIdPattern);
    }

    @Test
    fun `regex should match traceId`() {
        val sampleOutput = "[occurredAt: 2023-09-29 09:09:41,890] [traceId=65167835e4c128a0f45041d4c4f54288] [span=f45041d4c4f54288] severity=INFO   [thread=reactor-http-nio-2] logger=HelloWorldHandler - Should be logged with the context\n"
        assertThat(sampleOutput).containsPattern(traceIdPattern);
    }
}
