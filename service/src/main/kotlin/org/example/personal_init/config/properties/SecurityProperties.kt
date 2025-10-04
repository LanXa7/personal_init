package org.example.personal_init.config.properties


import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.http.HttpMethod

@ConfigurationProperties(prefix = "security")
data class SecurityProperties(
    val exposedEndpoints: List<ExposedEndpoint>
) {
    data class ExposedEndpoint(
        val method: HttpMethod?,
        val path: String
    )
}