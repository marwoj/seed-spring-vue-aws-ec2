package pl.seed.core

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@ComponentScan(
    basePackages = [
        "pl.seed.core",
        "pl.seed.domain",
        "pl.seed.mail"
    ]
)
@EnableReactiveMongoRepositories(
    basePackages = [
        "pl.seed.domain"
    ]
)
class CoreConfiguration
