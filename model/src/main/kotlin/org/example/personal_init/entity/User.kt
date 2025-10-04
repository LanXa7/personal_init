package org.example.personal_init.entity

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.OneToMany
import org.example.personal_init.base.BaseEntity

@Entity
interface User : BaseEntity {

    val username: String

    @OneToMany(mappedBy = "user")
    val accounts: List<Account>
}