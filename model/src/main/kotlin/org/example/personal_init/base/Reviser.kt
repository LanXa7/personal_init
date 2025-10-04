package org.example.personal_init.base

import org.babyfish.jimmer.sql.MappedSuperclass

@MappedSuperclass
interface Reviser {
    /**
     * 修改人Id
     */
    val reviserId: Long

    /**
     * 修改人名字
     */
    val reviserName: String
}