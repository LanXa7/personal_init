package org.example.personal_init.domain.account.service

import org.example.personal_init.domain.account.AccountFetchers
import org.example.personal_init.domain.account.AccountRepository
import org.example.personal_init.domain.account.CaptchaLoginStrategy
import org.example.personal_init.entity.Account
import org.example.personal_init.entity.dto.AuthCaptchaLoginInput
import org.example.personal_init.entity.dto.AuthPasswordLoginInput
import org.example.personal_init.enums.CaptchaReceivingMethod
import org.example.personal_init.enums.CaptchaReceivingMethod.*
import org.springframework.stereotype.Service

@Service
class AuthService(
    captchaLoginStrategy: List<CaptchaLoginStrategy>,
    private val accountRepository: AccountRepository
) {

    val captchaLoginStrategyMap =
        captchaLoginStrategy.associateBy { it.captchaReceivingMethod }


    fun getAccountWithRoleAndPermissionById(userId: Long) =
        accountRepository.queryAccountWithRoleAndPermissionById(
            userId,
            AccountFetchers.ACCOUNT_WITH_ROLE_AND_PERMISSION_FETCHER
        )


    fun loginByPassword(input: AuthPasswordLoginInput) {

    }

    fun loginByCaptcha(input: AuthCaptchaLoginInput): Account {
        val captchaLoginStrategy = captchaLoginStrategyMap[input.type]
            ?: throw IllegalArgumentException("captcha receiving method not found")
        if (!captchaLoginStrategy.verify(input.captcha)) {
            throw IllegalArgumentException("captcha is not valid")
        }
        return captchaLoginStrategy.getInfo(input.key)
    }

}