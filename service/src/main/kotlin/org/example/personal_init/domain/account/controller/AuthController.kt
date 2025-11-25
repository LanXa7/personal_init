package org.example.personal_init.domain.account.controller

import org.example.personal_init.domain.account.service.AuthService
import org.example.personal_init.entity.dto.AuthCaptchaLoginInput
import org.example.personal_init.entity.dto.AuthPasswordLoginInput
import org.example.personal_init.enums.CaptchaReceivingMethod
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @GetMapping("/captcha")
    fun getCaptcha(
        @RequestParam key: String, @RequestParam captchaReceivingMethod: CaptchaReceivingMethod) =
        authService.getCaptcha(key, captchaReceivingMethod)
    

    @PostMapping("/login/password")
    fun loginByPassword(@RequestBody input: AuthPasswordLoginInput): Unit =
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