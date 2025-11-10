package org.example.personal_init.domain.account.controller

import org.example.personal_init.domain.account.service.AuthService
import org.example.personal_init.entity.dto.AuthCaptchaLoginInput
import org.example.personal_init.entity.dto.AuthPasswordLoginInput
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login/password")
    fun loginByPassword(@RequestBody input: AuthPasswordLoginInput) =
        authService.loginByPassword(input)

    @PostMapping("/login/captcha")
    fun loginByCaptcha(@RequestBody input: AuthCaptchaLoginInput) {
        require(input.type != null) {
            throw IllegalArgumentException("captcha receiving method must be not null")
        }
        authService.loginByCaptcha(input)
    }


    @PostMapping("/register")
    fun register() {

    }

    @DeleteMapping("/logout")
    fun logout() {

    }
}