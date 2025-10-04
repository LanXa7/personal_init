package com.example.config.properties


import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.http.HttpMethod

@ConfigurationProperties(prefix = "security")
data class SecurityProperties @ConstructorBinding constructor(
    val exposedEndpoints: List<ExposedEndpoint>
) {
    data class ExposedEndpoint(
        val method: HttpMethod?,
        val path: String
    )
}