package pl.seed.domain

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface VisitorIpRepository : ReactiveMongoRepository<VisitorIp, String>
