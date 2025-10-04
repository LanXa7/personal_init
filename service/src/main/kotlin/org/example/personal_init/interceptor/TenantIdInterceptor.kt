package org.example.personal_init.interceptor

import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.sql.DraftInterceptor
import org.example.personal_init.base.TenantId
import org.example.personal_init.base.TenantIdDraft
import org.springframework.stereotype.Component

@Component
class TenantIdInterceptor : DraftInterceptor<TenantId, TenantIdDraft> {

    override fun beforeSave(draft: TenantIdDraft, original: TenantId?) {

        if (isLoaded(draft, TenantId::tenantId)) {
            return
        }

        // insert
        if (original === null) {
//            val currentUser = CurrentUserHolder.get()
//            draft.tenantId = currentUser.tenantId
        }
    }
}
