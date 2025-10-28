package org.example.personal_init.entity

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table
import org.example.personal_init.base.BaseEntity

@Entity
@Table(name = "\"user\"")
interface User : BaseEntity {

    val username: String

    @OneToMany(mappedBy = "user")
    val accounts: List<Account>
}