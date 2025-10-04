package org.example.personal_init.base

import org.babyfish.jimmer.sql.MappedSuperclass
import java.time.Instant

@MappedSuperclass
interface CreatedTime {
    /**
     * 创建时间
     */
    val createdTime: Instant
}