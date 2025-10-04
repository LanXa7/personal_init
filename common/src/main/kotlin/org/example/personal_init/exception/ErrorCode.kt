package org.example.personal_init.exception

enum class ErrorCode(val code: Int, val message: String) {
    /**
     * 枚举值没有定义
     */
    ENUM_VALUE_IS_NOT_DEFINE(405, "value is not defined"),

    TRANSACTION_ERROR(50000, "transaction error"),

    USER_NOT_FOUND(50010, "user not found"),

    /**
     * 用户名或密码错误
     */
    USERNAME_OR_PASSWORD_ERROR(50011, "用户名或密码错误"),

    /**
     * 用户名或邮箱已存在
     */
    USERNAME_OR_EMAIL_ALREADY_EXISTS(50012, "用户名或邮箱已存在"),

    /**
     * 验证码不存在
     */
    EMAIL_CODE_IS_NOT_EXIST(50013, "邮箱验证码不存在"),

    /**
     * 验证码不正确
     */
    EMAIL_CODE_IS_NOT_TRUE(50014, "邮箱验证码不正确"),

    GET_EMAIL_MANY_ONE_HOUR(50015, "get three verification code within a hour"),

    GET_EMAIL_MANY_ONE_MINUTE(50016, "get three verification code within a minute"),

    USER_IS_DISABLED(50017, "user is disabled"),

}