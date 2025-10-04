package org.example.personal_init.interceptor

import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.sql.DraftInterceptor
import org.example.personal_init.base.Creator
import org.example.personal_init.base.CreatorDraft
import org.springframework.stereotype.Component

@Component
class CreatorInterceptor : DraftInterceptor<Creator, CreatorDraft> {

    override fun beforeSave(draft: CreatorDraft, original: Creator?) {

        if (isLoaded(draft, Creator::creatorId) || isLoaded(draft, Creator::creatorName)) {
            throw IllegalStateException("不允许手动设置 creatorId, creatorName 字段")
        }

        // insert
        if (original === null) {
//            val currentUser = CurrentUserHolder.get()
//            draft.creatorId = currentUser.id
//            draft.creatorName = currentUser.name
        }
    }
}
