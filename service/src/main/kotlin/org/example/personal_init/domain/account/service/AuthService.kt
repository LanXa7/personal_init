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
import java.util.UUID
import kotlin.collections.get

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
        val captchaLoginStrategy = getCaptchaLoginStrategy(input.type!!)
        if (!captchaLoginStrategy.verify(input.captcha)) {
            throw IllegalArgumentException("captcha is not valid")
        }
        return captchaLoginStrategy.getInfo(input.key)
    }

    fun getCaptcha(key: String, captchaReceivingMethod: CaptchaReceivingMethod) {
        val captchaLoginStrategy = getCaptchaLoginStrategy(captchaReceivingMethod)
        val code = UUID.randomUUID().toString().substring(0, 6)
        captchaLoginStrategy.send(key, code)
    }

    private fun getCaptchaLoginStrategy(captchaReceivingMethod: CaptchaReceivingMethod) =
        captchaLoginStrategyMap[captchaReceivingMethod]
            ?: throw IllegalArgumentException("captcha receiving method not found")

}