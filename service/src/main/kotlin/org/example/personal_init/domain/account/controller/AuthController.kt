package org.example.personal_init.domain.account.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController {

    @PostMapping("/login")
    fun login() {

    }

    @PostMapping("/register")
    fun register() {

    }

    @DeleteMapping("/logout")
    fun logout() {

    }
}