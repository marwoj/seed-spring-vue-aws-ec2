package pl.seed.domain

import java.time.Instant

data class VisitorIp(
    val ip: String,
    val timestamp: Instant,
    val id: String? = null,
)
