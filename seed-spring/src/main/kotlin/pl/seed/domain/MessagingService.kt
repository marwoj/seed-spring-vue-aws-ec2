package pl.seed.domain

import org.springframework.stereotype.Service
import pl.seed.mail.EmailService
import pl.seed.mail.HelloEmail
import reactor.core.publisher.Mono

@Service
class MessagingService(
    private val emailService: EmailService
) {
    fun send(mail: HelloEmail): Mono<Void> = Mono.just(emailService.send(mail)).then()
}
