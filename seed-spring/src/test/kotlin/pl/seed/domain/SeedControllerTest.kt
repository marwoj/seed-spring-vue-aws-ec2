package pl.seed.domain

import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import pl.seed.AbstractControllerTest
import pl.seed.mail.HelloEmail
import pl.seed.mail.RecordingEmailService
import pl.seed.testcontainers.MongoSpringBootTest
import pl.seed.time.Clock
import java.time.Instant

@MongoSpringBootTest
class SeedControllerTest(
    private val repository: VisitorIpRepository,
    @RegisterExtension @JvmField val emailService: RecordingEmailService
) : AbstractControllerTest() {

    @MockBean
    private lateinit var clock: Clock

    @BeforeEach
    fun resetDb() {
        repository.deleteAll().block()
    }

    @Test
    fun `returns recent visitor data and saves it in database`() {
        whenever(clock.now()).thenReturn(Instant.parse("2020-12-06T00:00:00Z"))

        webClient.get().uri("/public/visitors")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.timestamp").isEqualTo("2020-12-06T00:00:00Z")

        val visitors = repository.findAll().collectList().block()!!
        assertThat(visitors).hasSize(1)
        assertThat(visitors[0].timestamp).isEqualTo(Instant.parse("2020-12-06T00:00:00Z"))
    }

    @Test
    fun `sends email with requested data`() {
        webClient.post().uri("/mail")
            .headers { head -> head.setBasicAuth("changeme", "changeme") }
            .bodyValue(HelloEmail(title = "Title", body = "Hi", to = "to@mail.com"))
            .exchange()
            .expectStatus().isOk

        assertThat(emailService.emails).hasSize(1)
        val submittedEmail = emailService.findOne<HelloEmail>()
        assertThat(submittedEmail).isEqualTo(HelloEmail(title = "Title", body = "Hi", to = "to@mail.com"))
    }

    @Test
    fun `validates request`() {
        val payload: String = """
           {
           "title":"Title",
           "body": "Hi",
           "to": "invalid-mail"
           }
        """.trimIndent()

        webClient.post().uri("/mail")
            .headers { head -> head.setBasicAuth("changeme", "changeme") }
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(payload)
            .exchange()
            .expectStatus().isBadRequest

        assertThat(emailService.emails).hasSize(0)
        // TODO how to validate error message using WebTestClient?
        // assertThat(errorMessage).contains("must be a well-formed email address")
    }

    @Test
    fun `validates access when sending email`() {
        webClient.post().uri("/mail")
            .headers { head -> head.setBasicAuth("changeme", "wrong-pass") }
            .bodyValue(HelloEmail(title = "Title", body = "Hi", to = "to@mail.com"))
            .exchange()
            .expectStatus().isUnauthorized
    }
}
