package pl.seed.domain

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.seed.mail.HelloEmail
import reactor.core.publisher.Mono
import java.time.Instant
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
class SeedController(
    private val visitorIpRepository: VisitorIpRepository,
    private val messagingService: MessagingService
) {

    @GetMapping("/public/visitors")
    fun getVisitors(request: HttpServletRequest): Mono<VisitorIp> =
        visitorIpRepository.save(VisitorIp(request.remoteAddr, Instant.now()))
            .then(visitorIpRepository.findAll().last())

    @PostMapping("/mail")
    fun sendEmail(
        @RequestBody @Valid message: HelloEmail
    ): Mono<Void> = messagingService.send(message)
}
