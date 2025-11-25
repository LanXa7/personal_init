package org.example.personal_init.domain.account.service

import org.example.personal_init.domain.account.AccountFetchers
import org.example.personal_init.domain.account.AccountRepository
import org.example.personal_init.domain.account.LoginCaptchaChannelStrategy
import org.example.personal_init.entity.Account
import org.example.personal_init.entity.dto.AuthCaptchaLoginInput
import org.example.personal_init.entity.dto.AuthPasswordLoginInput
import org.example.personal_init.enums.CaptchaReceivingMethod
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    loginCaptchaChannelStrategy: List<LoginCaptchaChannelStrategy>,
    private val accountRepository: AccountRepository
) {

    val captchaLoginStrategyMap =
        loginCaptchaChannelStrategy.associateBy { it.captchaReceivingMethod }


    fun getAccountWithRoleAndPermissionById(userId: Long) =
        accountRepository.queryAccountWithRoleAndPermissionById(
            userId,
            AccountFetchers.ACCOUNT_WITH_ROLE_AND_PERMISSION_FETCHER
        )


    fun loginByPassword(input: AuthPasswordLoginInput) {
        val account = accountRepository.findByAccount(input.account)?: throw IllegalArgumentException("account not found")
        val checkPassword = BCrypt.checkpw(input.password, account.password)
        if(checkPassword){

        }else{
            throw IllegalArgumentException("password is not valid")
        }
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