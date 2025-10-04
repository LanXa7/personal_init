package org.example.personal_init.interceptor

import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.sql.DraftInterceptor
import org.example.personal_init.base.Reviser
import org.example.personal_init.base.ReviserDraft
import org.springframework.stereotype.Component

@Component
class ReviserInterceptor: DraftInterceptor<Reviser, ReviserDraft> {

    override fun beforeSave(draft: ReviserDraft, original: Reviser?) {

        if (isLoaded(draft, Reviser::reviserId) || isLoaded(draft, Reviser::reviserName)
        ) {
            throw IllegalStateException("不允许手动设置 reviserId, reviserName 字段")
        }

//        val currentUser = CurrentUserHolder.get()
//        draft.reviserId = currentUser.id
//        draft.reviserName = currentUser.name
    }
}
