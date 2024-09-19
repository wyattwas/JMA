package org.opencodespace.jma

import com.google.gson.JsonParser
import okhttp3.Response
import org.opencodespace.jma.api.exception.JMARequestException
import org.opencodespace.jma.extension.toPageable
import java.lang.Exception

open class MastodonRequest<T>(
        private val executor: () -> Response,
        private val mapper: (String) -> Any
) {
    interface Action1<T> {
        fun invoke(arg: T)
    }

    private var action: (String) -> Unit = {}

    private var isPageable: Boolean = false

    internal fun toPageable() = apply {
        isPageable = true
    }

    @JvmSynthetic
    fun doOnJson(action: (String) -> Unit) = apply {
        this.action = action
    }

    fun doOnJson(action: Action1<String>) = apply {
        this.action = { action.invoke(it) }
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(JMARequestException::class)
    fun execute(): T {
        val response = executor()
        if (response.isSuccessful) {
            try {
                val body = response.body.toString()
                val element = JsonParser().parse(body)
                if (element.isJsonObject) {
                    action(body)
                    return mapper(body) as T
                } else {
                    val list = arrayListOf<Any>()
                    element.asJsonArray.forEach {
                        val json = it.toString()
                        action(json)
                        list.add(mapper(json))
                    }
                    if (isPageable) {
                        return list.toPageable(response) as T
                    } else {
                        return list as T
                    }
                }
            } catch (e: Exception) {
                throw JMARequestException(e)
            }
        } else {
            throw JMARequestException(response)
        }
    }
}