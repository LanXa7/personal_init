package org.example.personal_init.config.security

import org.example.personal_init.exception.UserNotFoundException
import org.example.personal_init.service.account.AuthService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val authService: AuthService
) : UserDetailsService {

    fun loadUserByUserId(userId: Long): UserDetails {
        val account = authService.getAccountWithRoleAndPermissionById(userId)
            ?: throw UserNotFoundException()
        val roles = account.roles.map { it.code }
        val permissions = account.roles.flatMap { it.permissions }.map { it.code }.distinct()
        return User.builder()
            .username(account.accountName)
            .password(account.password)
            .roles(*roles.toTypedArray())
            .authorities(*permissions.toTypedArray())
            .build()
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        return username?.let {
            loadUserByUserId(it.toLong())
        } ?: throw UserNotFoundException()
    }

}