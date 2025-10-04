package org.example.personal_init.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(value = "spring.data.redis")
data class RedisProperties(
    val host: String,
    val port: Int,
    val password: String,
    val database: Int
)