package org.example.personal_init.domain.account

import org.babyfish.jimmer.spring.repo.support.AbstractKotlinRepository
import org.babyfish.jimmer.sql.fetcher.Fetcher
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.example.personal_init.entity.Account
import org.example.personal_init.entity.account
import org.example.personal_init.entity.email
import org.example.personal_init.entity.id
import org.example.personal_init.entity.phone
import org.springframework.stereotype.Repository

@Repository
class AccountRepository(
    sql: KSqlClient
) : AbstractKotlinRepository<Account, Long>(sql) {

    fun queryAccountWithRoleAndPermissionById(accountId: Long, fetcher: Fetcher<Account>? = null) =
        createQuery {
            where(table.id eq accountId)
            select(table.fetch(fetcher))
        }.fetchOneOrNull()

    fun findByEmail(key: String, fetcher: Fetcher<Account>? = null) =
        createQuery {
            where(table.email eq key)
            select(table.fetch(fetcher))
        }.fetchOne()

    fun findByPhone(key: String, fetcher: Fetcher<Account>? = null) =
        createQuery {
            where(table.phone eq key)
            select(table.fetch(fetcher))
        }.fetchOne()

    fun findByAccount(account: String, fetcher: Fetcher<Account>? = null) =
        createQuery {
            where(table.account eq account)
            select(table.fetch(fetcher))
        }.fetchOneOrNull()
}