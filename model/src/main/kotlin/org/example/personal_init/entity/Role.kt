package org.example.personal_init.entity

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.JoinTable
import org.babyfish.jimmer.sql.ManyToMany
import org.example.personal_init.base.BaseEntity

@Entity
interface Role : BaseEntity {
    val name: String

    val code: String

    @ManyToMany
    @JoinTable(
        name = "account_role_mapping",
        joinColumnName = "role_id",
        inverseJoinColumnName = "account_id"
    )
    val accounts: List<Account>

    @ManyToMany(mappedBy = "roles")
    val permissions: List<Permission>
}