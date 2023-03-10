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

    /**
     * when @AutoConfigureObservability is present and
     * property management.baggage.correlation.fields is configured
     * "Misalignment: popped updateScope false !=  expected false"
     * happens
     */
    @Test
    fun `should return 200 ok`() {
        testClient
            .get()
            .uri("/helloWorld")
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .expectStatus()
            .is2xxSuccessful
    }
}
