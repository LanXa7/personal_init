package org.example.personal_init.entity

import org.babyfish.jimmer.sql.Entity
import org.example.personal_init.base.LongId

@Entity
interface RbacPolicy : LongId {
    val type: String
    val v0: String
    val v1: String
    val v2: String?
    val v3: String?
    val v4: String?
    val v5: String?
}