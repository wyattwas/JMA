package org.opencodespace.jma.extension

import org.opencodespace.jma.api.Link
import org.opencodespace.jma.api.Pageable
import okhttp3.Headers
import okhttp3.Response

fun <T> List<T>.toPageable(response: Response): Pageable<T> {
    val linkHeader = response.header("link")
    val link = Link.parse(linkHeader)
    return Pageable(this, link)
}
