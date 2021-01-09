package pl.seed.domain

import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.seed.mail.HelloEmail
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
class SeedController(
    private val seedService: SeedService
) {
    @GetMapping("/public/visitors")
    fun getVisitors(request: ServerHttpRequest): Mono<VisitorIp> =
        seedService.logRequest(request)

    @PostMapping("/mail")
    fun sendEmail(
        @RequestBody @Valid message: HelloEmail
    ): Mono<Void> = seedService.sendEmail(message)
}
