package org.example.personal_init.config.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class JwtAuthenticationToken : AbstractAuthenticationToken {

    private val principal: Any
    private var credentials: String?

    constructor(principal: Any, credentials: String?) : super(null) {
        this.principal = principal
        this.credentials = credentials
        isAuthenticated = false
    }

    constructor(
        principal: Any,
        credentials: String,
        authorities: Collection<GrantedAuthority>
    ) : super(authorities) {
        this.principal = principal
        this.credentials = credentials
        isAuthenticated = true
    }

    override fun getCredentials(): String? = this.credentials

    override fun getPrincipal(): Any = this.principal

    companion object {
        fun unauthenticated(userIdentify: String, token: String): JwtAuthenticationToken {
            return JwtAuthenticationToken(userIdentify, token)
        }

        fun authenticated(
            principal: UserDetails,
            token: String,
            authorities: Collection<GrantedAuthority>
        ): JwtAuthenticationToken {
            return JwtAuthenticationToken(principal, token, authorities)
        }
    }
}