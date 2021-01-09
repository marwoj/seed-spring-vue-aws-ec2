package pl.seed.domain

import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Service
import pl.seed.mail.EmailService
import pl.seed.mail.HelloEmail
import pl.seed.time.Clock
import reactor.core.publisher.Mono

@Service
class SeedService(
    private val clock: Clock,
    private val visitorIpRepository: VisitorIpRepository,
    private val emailService: EmailService
) {
    fun sendEmail(mail: HelloEmail): Mono<Void> = Mono.just(emailService.send(mail)).then()

    fun logRequest(request: ServerHttpRequest): Mono<VisitorIp> =
        visitorIpRepository.save(VisitorIp(request.remoteAddress?.address?.hostAddress.toString(), clock.now()))
            .then(visitorIpRepository.findAll().last())
}
