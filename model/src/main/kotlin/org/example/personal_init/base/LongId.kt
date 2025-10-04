package org.example.personal_init.base

import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.MappedSuperclass

@MappedSuperclass
interface LongId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long
}