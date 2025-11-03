package org.example.personal_init.domain.account

import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.example.personal_init.entity.Account
import org.example.personal_init.entity.by

object AccountFetchers {
    val ACCOUNT_WITH_ROLE_AND_PERMISSION_FETCHER = newFetcher(Account::class).by {
        accountName()
        password()
        roles {
            code()
            permissions {
                code()
            }
        }
    }
}