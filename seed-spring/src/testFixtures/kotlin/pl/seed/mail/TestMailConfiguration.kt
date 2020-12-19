package pl.seed.mail

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@TestConfiguration
class TestMailConfiguration {
    @Bean
    @Primary
    fun emailService() = RecordingEmailService()
}
