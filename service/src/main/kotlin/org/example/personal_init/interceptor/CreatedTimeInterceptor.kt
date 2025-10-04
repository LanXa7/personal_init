package org.example.personal_init.interceptor

import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.sql.DraftInterceptor
import org.example.personal_init.base.CreatedTime
import org.example.personal_init.base.CreatedTimeDraft
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class CreatedTimeInterceptor : DraftInterceptor<CreatedTime, CreatedTimeDraft> {

    override fun beforeSave(draft: CreatedTimeDraft, original: CreatedTime?) {

        if (isLoaded(draft, CreatedTime::createdTime)) {
            throw IllegalStateException("不允许手动设置 createdTime 字段")
        }

        // insert
        if (original === null) {
            draft.createdTime = Instant.now()
        }
    }
}
