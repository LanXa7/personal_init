package org.example.personal_init.service.account

import org.babyfish.jimmer.client.meta.DefaultFetcherOwner
import org.springframework.stereotype.Service

@Service
@DefaultFetcherOwner(AccountFetchers::class)
class AuthService(
    private val accountRepository: AccountRepository
) {

    fun getAccountWithRoleAndPermissionById(userId: Long) =
        accountRepository.queryAccountWithRoleAndPermissionById(
            userId,
            AccountFetchers.ACCOUNT_WITH_ROLE_AND_PERMISSION_FETCHER
        )

}