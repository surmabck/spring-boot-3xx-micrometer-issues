package com.example.demo

import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@AutoConfigureObservability
@SpringBootTest
@ExtendWith(OutputCaptureExtension::class)
class DemoApplicationTest(@Autowired private val context: ApplicationContext) {
    private val testClient = WebTestClient.bindToApplicationContext(context).build()
    private val traceIdRegex = Regex("\\[traceId=[a-z0-9]{32}\\]")

    @Test
    fun `should log traceId in WebExceptionHandler`(capturedOutput: CapturedOutput) {
        testClient
            .get()
            .uri("/failing")
            .exchange()

        val log = capturedOutput.all.split(System.lineSeparator())
            .firstOrNull { it.contains("logger=GlobalErrorHandler") }


        log.toString() shouldContain traceIdRegex
    }

    @Test
    fun `should log traceId in ResponseLoggingFilter when error has been handled by WebExceptionHandler`(capturedOutput: CapturedOutput) {
        testClient
            .get()
            .uri("/failing")
            .exchange()

        val log = capturedOutput.all.split(System.lineSeparator())
            .firstOrNull { it.contains("logger=ResponseLoggingFilter") }

        log.toString() shouldContain traceIdRegex
    }

    @Test
    fun `should log traceId in ResponseLoggingFilter when no errors occured`(capturedOutput: CapturedOutput) {
        testClient
            .get()
            .uri("/helloWorld")
            .exchange()

        val log = capturedOutput.all.split(System.lineSeparator())
            .firstOrNull { it.contains("logger=ResponseLoggingFilter") }

        log.toString() shouldContain traceIdRegex
    }
}
