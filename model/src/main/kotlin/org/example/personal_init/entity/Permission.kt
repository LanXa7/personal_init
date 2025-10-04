package org.example.personal_init.entity

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.JoinTable
import org.babyfish.jimmer.sql.ManyToMany
import org.example.personal_init.base.BaseEntity

@Entity
interface Permission : BaseEntity {
    val name: String

    val code: String

    @ManyToMany
    @JoinTable(
        name = "role_permission_mapping",
        joinColumnName = "permission_id",
        inverseJoinColumnName = "role_id"
    )
    val roles: List<Role>
}