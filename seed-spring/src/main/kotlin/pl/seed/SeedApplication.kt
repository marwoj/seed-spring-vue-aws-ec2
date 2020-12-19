package pl.seed

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import pl.seed.core.CoreConfiguration

@SpringBootApplication
@Import(CoreConfiguration::class)
class SeedApplication

fun main(args: Array<String>) {
    runApplication<SeedApplication>(*args)
}
