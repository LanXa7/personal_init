package org.example.personal_init.exception

class UserNotFoundException() :
    BusinessException(ErrorCode.USER_NOT_FOUND)

class EmailSendLimitException(errorCode: ErrorCode) :
    BusinessException(errorCode)

class UsernameOrPasswordErrorException :
    BusinessException(ErrorCode.USERNAME_OR_PASSWORD_ERROR)

class UsernameOrEmailAlreadyExistsException :
    BusinessException(ErrorCode.USERNAME_OR_EMAIL_ALREADY_EXISTS)

class CodeIsNotExistsException :
    BusinessException(ErrorCode.EMAIL_CODE_IS_NOT_EXIST)

class CodeIsNotTrueException :
    BusinessException(ErrorCode.EMAIL_CODE_IS_NOT_TRUE)

class UserIsDisabledException() :
    BusinessException(ErrorCode.USER_IS_DISABLED)