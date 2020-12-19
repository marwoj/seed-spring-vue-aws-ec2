package pl.seed.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import pl.seed.AbstractControllerTest
import pl.seed.mail.HelloEmail
import pl.seed.mail.RecordingEmailService
import pl.seed.testcontainers.MongoSpringBootTest

@MongoSpringBootTest
class SeedControllerTest(
    private val repository: VisitorIpRepository,
    @RegisterExtension @JvmField val emailService: RecordingEmailService
) : AbstractControllerTest() {
    @BeforeEach
    fun resetDb() {
        repository.deleteAll().block()
    }

    @Test
    fun `returns recent visitor data and saves it in database`() {
        performAsync(get("/public/visitors"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.ip").value("127.0.0.1"))

        val visitors = repository.findAll().collectList().block()!!
        assertThat(visitors).hasSize(1)
        assertThat(visitors[0].ip).isEqualTo("127.0.0.1")
    }

    @Test
    fun `sends email with requested data`() {
        performAsync(
            post("/mail")
                .content(toJson(HelloEmail(title = "Title", body = "Hi", to = "to@mail.com")))
                .with(httpBasic("changeme", "changeme"))
        ).andExpect(status().isOk)

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

        val errorMessage = mvc.perform(
            post("/mail").content(payload)
                .with(httpBasic("changeme", "changeme"))
        )
            .andExpect(status().isBadRequest)
            .andReturn().resolvedException!!.message

        assertThat(emailService.emails).hasSize(0)
        assertThat(errorMessage).contains("must be a well-formed email address")
    }

    @Test
    fun `validates access when sending email`() {
        mvc.perform(
            post("/mail")
                .content(toJson(HelloEmail(title = "Title", body = "Hi", to = "to@mail.com")))
                .with(httpBasic("changeme", "wrong-pass"))
        ).andExpect(status().isUnauthorized)
    }
}
