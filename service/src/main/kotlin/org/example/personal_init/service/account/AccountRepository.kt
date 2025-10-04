package org.example.personal_init.service.account

import org.babyfish.jimmer.spring.repo.support.AbstractKotlinRepository
import org.babyfish.jimmer.sql.fetcher.Fetcher
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.example.personal_init.entity.Account
import org.example.personal_init.entity.id
import org.springframework.stereotype.Repository

@Repository
class AccountRepository(
    sql: KSqlClient
) : AbstractKotlinRepository<Account, Long>(sql) {

    fun queryAccountWithRoleAndPermissionById(accountId: Long, fetcher: Fetcher<Account>) =
        createQuery {
            where(table.id eq accountId)
            select(table.fetch(fetcher))
        }.fetchOneOrNull()
}