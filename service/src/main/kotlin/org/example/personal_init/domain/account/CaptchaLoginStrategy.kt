package org.example.personal_init.domain.account

import org.example.personal_init.entity.Account
import org.example.personal_init.enums.CaptchaReceivingMethod
import org.example.personal_init.util.JwtUtil
import org.example.personal_init.util.RedissonUtil
import org.springframework.stereotype.Component

interface CaptchaLoginStrategy {
    val captchaReceivingMethod: CaptchaReceivingMethod
    fun verify(captcha: String): Boolean
    fun getInfo(key: String): Account
}

@Component
class EmailCaptchaLogin(
    private val redissonUtil: RedissonUtil,
    private val accountRepository: AccountRepository
) : CaptchaLoginStrategy {
    override val captchaReceivingMethod = CaptchaReceivingMethod.EMAIL

    override fun verify(captcha: String): Boolean =
        captcha == redissonUtil.auth.getVerifyCode(captcha, captchaReceivingMethod)

    override fun getInfo(key: String): Account =
        accountRepository.findByEmail(key)
}

@Component
class PhoneCaptchaLogin(
    private val redissonUtil: RedissonUtil,
    private val accountRepository: AccountRepository
) : CaptchaLoginStrategy {
    override val captchaReceivingMethod = CaptchaReceivingMethod.PHONE

    override fun verify(captcha: String): Boolean =
        captcha == redissonUtil.auth.getVerifyCode(captcha, captchaReceivingMethod)

    override fun getInfo(key: String): Account =
        accountRepository.findByPhone(key)
}