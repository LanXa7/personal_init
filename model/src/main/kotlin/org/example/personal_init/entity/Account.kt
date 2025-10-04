package org.example.personal_init.entity

import org.babyfish.jimmer.sql.*
import org.example.personal_init.base.BaseEntity

@Entity
interface Account : BaseEntity {

    @Key
    val accountName: String

    @Key
    val phone: String

    val password: String

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User

    @ManyToMany(mappedBy = "accounts")
    val roles: List<Role>
}