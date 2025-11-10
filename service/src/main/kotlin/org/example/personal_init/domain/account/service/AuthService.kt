package org.example.personal_init.domain.account.service

import org.example.personal_init.domain.account.AccountFetchers
import org.example.personal_init.domain.account.AccountRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val accountRepository: AccountRepository
) {

    fun getAccountWithRoleAndPermissionById(userId: Long) =
        accountRepository.queryAccountWithRoleAndPermissionById(
            userId,
            AccountFetchers.ACCOUNT_WITH_ROLE_AND_PERMISSION_FETCHER
        )

}