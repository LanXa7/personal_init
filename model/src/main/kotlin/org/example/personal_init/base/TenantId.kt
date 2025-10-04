package org.example.personal_init.base

import org.babyfish.jimmer.sql.MappedSuperclass

@MappedSuperclass
interface TenantId {
    /**
     * 租户Id
     */
    val tenantId: Long
}