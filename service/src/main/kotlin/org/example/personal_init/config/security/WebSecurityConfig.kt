package org.example.personal_init.config.security

import org.example.personal_init.config.properties.SecurityProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties(SecurityProperties::class)
class WebSecurityConfig(
    private val securityProperties: SecurityProperties,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    fun authenticationManager(
        authenticationConfig: AuthenticationConfiguration
    ): AuthenticationManager =
        authenticationConfig.authenticationManager

    @Bean
    fun securityFilterChina(http: HttpSecurity): SecurityFilterChain {
        val handler = RestfulAuthenticationEntryPointHandler()
        val exposedEndpoints = securityProperties.exposedEndpoints
        return http
            .securityContext { it.disable() }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                exposedEndpoints.forEach { endpoint ->
                    if (endpoint.method?.name().equals("ANY")) {
                        it
                            .requestMatchers(endpoint.path)
                            .permitAll()
                    } else {
                        it
                            .requestMatchers(endpoint.method, endpoint.path)
                            .permitAll()
                    }
                }
                it.anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .exceptionHandling {
                it
                    .accessDeniedHandler(handler)
                    .accessDeniedHandler(handler)
            }
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )
            .build()
    }

}