package pl.seed.time

import org.springframework.stereotype.Component
import java.time.Instant

@Component
class Clock {
    fun now(): Instant = Instant.now()
}
