package org.example.personal_init.base

import org.babyfish.jimmer.sql.MappedSuperclass

@MappedSuperclass
interface Creator {
    /**
     * 创建人id
     */
    val creatorId: Long

    /**
     * 创建人名字
     */
    val creatorName: String
}