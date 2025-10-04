package org.example.personal_init.base

import org.babyfish.jimmer.sql.MappedSuperclass

@MappedSuperclass
interface BaseEntity : LongId, Creator, CreatedTime, Reviser, RevisedTime {
}