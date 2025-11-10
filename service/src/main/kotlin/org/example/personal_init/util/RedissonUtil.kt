package org.example.personal_init.util

import org.example.personal_init.Const
import org.example.personal_init.enums.CaptchaReceivingMethod
import org.redisson.api.*
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedissonUtil(
    redissonClient: RedissonClient
) {
    val jwt = JwtModule(redissonClient)
    val auth = AuthModule(redissonClient)
}


class JwtModule(private val redissonClient: RedissonClient) {

    private fun jwtBucket(uuid: String) =
        redissonClient.getBucket<String>(
            "${Const.JWT_BLACK_LIST}$uuid"
        )

    fun isInvalidToken(uuid: String): Boolean =
        jwtBucket(uuid).isExists

    fun addBlacklist(uuid: String, expire: Long) =
        jwtBucket(uuid).set("", Duration.ofMillis(expire))


}

class AuthModule(private val redissonClient: RedissonClient) {

    fun getLock(key: String): RLock =
        redissonClient.getLock(key)


    private fun verifyCodeBucket(key: String, captchaReceivingMethod: CaptchaReceivingMethod) =
        when (captchaReceivingMethod) {
            CaptchaReceivingMethod.EMAIL -> redissonClient.getBucket<String>(Const.VERIFY_EMAIL_DATA + key)
            CaptchaReceivingMethod.PHONE -> redissonClient.getBucket<String>(Const.VERIFY_PHONE_DATA + key)
        }

    fun setVerifyCode(key: String, code: String, captchaReceivingMethod: CaptchaReceivingMethod) =
        verifyCodeBucket(key, captchaReceivingMethod).set(code, Duration.ofMinutes(Const.CODE_EXPIRED))

    fun getVerifyCode(key: String, captchaReceivingMethod: CaptchaReceivingMethod): String? =
        verifyCodeBucket(key, captchaReceivingMethod).get()

    fun deleteVerifyCode(key: String, captchaReceivingMethod: CaptchaReceivingMethod) =
        verifyCodeBucket(key, captchaReceivingMethod).delete()

    private fun captchaMinuteAtomicLong(name: String, asIp: Boolean = false): RAtomicLong {
        val atomicLong = if (asIp) {
            redissonClient.getAtomicLong("captcha:atomicLong:minute:ip:$name")
        } else {
            redissonClient.getAtomicLong("captcha:atomicLong:email:$name")
        }
        atomicLong.expire(Duration.ofMinutes(1))
        return atomicLong
    }

    fun addCaptchaMinuteAtomicLong(name: String, asIp: Boolean = false) =
        captchaMinuteAtomicLong(name, asIp).getAndIncrement().toInt()

    private fun captchaHourRateLimiter(name: String, asIp: Boolean = false): RRateLimiter {
        val rateLimiter = if (asIp) {
            redissonClient.getRateLimiter("captcha:rateLimiter:minute:ip:$name")
        } else {
            redissonClient.getRateLimiter("captcha:rateLimiter:minute:email:$name")
        }
        rateLimiter.trySetRate(RateType.OVERALL, 3, Duration.ofHours(1))
        rateLimiter.expire(Duration.ofHours(1))
        return rateLimiter
    }

    fun tryAcquireCaptchaHourRateLimiter(name: String, asIp: Boolean = false) =
        captchaHourRateLimiter(name, asIp).tryAcquire()


}