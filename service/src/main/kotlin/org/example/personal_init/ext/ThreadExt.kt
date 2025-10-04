package org.example.personal_init.ext

import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder

// 定义 ScopedValue 来存储请求属性
private val REQUEST_ATTRIBUTES_SCOPE = ScopedValue.newInstance<RequestAttributes>()

fun vt(
    asStart: Boolean = true,
    name: String? = null,
    contextClassLoader: ClassLoader? = null,
    block: () -> Unit
): Thread {
    val requestAttributes: RequestAttributes? = RequestContextHolder.getRequestAttributes()

    val builder = Thread.ofVirtual().also { vt ->
        name?.let { vt.name(name) }
    }

    val thread = builder.unstarted {
        try {
            // 使用 ScopedValue 来绑定请求属性
            if (requestAttributes != null) {
                ScopedValue.where(REQUEST_ATTRIBUTES_SCOPE, requestAttributes).run {
                    withContextClassLoader(contextClassLoader) {
                        runWithRequestAttributes(block)
                    }
                }
            } else {
                withContextClassLoader(contextClassLoader) {
                    block()
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
        // 注意：ScopedValue 会自动清理，不需要 finally 块
    }

    if (asStart) {
        thread.start()
    }
    return thread
}

private fun runWithRequestAttributes(block: () -> Unit) {
    // 在当前线程中设置 RequestAttributes
    RequestContextHolder.setRequestAttributes(REQUEST_ATTRIBUTES_SCOPE.get())
    try {
        block()
    } finally {
        RequestContextHolder.resetRequestAttributes()
    }
}

private fun withContextClassLoader(contextClassLoader: ClassLoader?, block: () -> Unit) {
    if (contextClassLoader != null) {
        val originalClassLoader = Thread.currentThread().contextClassLoader
        Thread.currentThread().contextClassLoader = contextClassLoader
        try {
            block()
        } finally {
            Thread.currentThread().contextClassLoader = originalClassLoader
        }
    } else {
        block()
    }
}