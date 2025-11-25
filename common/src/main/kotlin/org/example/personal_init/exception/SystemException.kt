package org.example.personal_init.exception

class TransactionErrorException :
    BusinessException(ErrorCode.TRANSACTION_ERROR)

class EnumValueIsNotDefineException:
        BusinessException(ErrorCode.ENUM_VALUE_IS_NOT_DEFINE)