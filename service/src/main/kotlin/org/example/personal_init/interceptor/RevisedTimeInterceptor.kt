package org.example.personal_init.interceptor

import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.sql.DraftInterceptor
import org.example.personal_init.base.RevisedTime
import org.example.personal_init.base.RevisedTimeDraft
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class RevisedTimeInterceptor : DraftInterceptor<RevisedTime, RevisedTimeDraft> {

    override fun beforeSave(draft: RevisedTimeDraft, original: RevisedTime?) {

        if (isLoaded(draft, RevisedTime::revisedTime)) {
            throw IllegalStateException("不允许手动设置 revisedTime 字段")
        }

        draft.revisedTime = Instant.now()
    }
}
