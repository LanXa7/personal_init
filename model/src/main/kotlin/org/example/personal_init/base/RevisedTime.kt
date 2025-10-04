package org.example.personal_init.base

import org.babyfish.jimmer.sql.MappedSuperclass
import java.time.Instant

@MappedSuperclass
interface RevisedTime {
    /**
     * 修改时间
     */
    val revisedTime: Instant
}