package org.opencodespace.jma.extension

import com.google.gson.Gson
import org.opencodespace.jma.api.exception.JMARequestException
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.Type

inline fun <reified T> Response.fromJson(gson: Gson, clazz: Class<T>): T {
    try {
        val json = body.toString()
        return gson.fromJson(json, clazz)
    } catch (e: IOException) {
        throw JMARequestException(e)
    }
}

inline fun <reified T> Response.fromJson(gson: Gson, type: Type): T {
    try {
        val json = body.toString()
        return gson.fromJson(json, type)
    } catch (e: IOException) {
        throw JMARequestException(e)
    }
}
