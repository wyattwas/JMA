package org.opencodespace.jma.api.method

import org.opencodespace.jma.MastodonClient
import org.opencodespace.jma.MastodonRequest
import org.opencodespace.jma.api.Pageable
import org.opencodespace.jma.api.Range
import org.opencodespace.jma.api.entity.Notification
import org.opencodespace.jma.api.exception.JMARequestException
import org.opencodespace.jma.extension.emptyRequestBody

/**
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#notifications
 */
class Notifications(private val client: MastodonClient) {
    // GET /api/v1/notifications
    @JvmOverloads
    fun getNotifications(range: Range = Range(), excludeTypes: List<Notification.Type>? = null): MastodonRequest<Pageable<Notification>> {
        val parameter = range.toParameter()
        if (excludeTypes != null) {
            parameter.append("exclude_types", excludeTypes.map { it.value })
        }
        return MastodonRequest<Pageable<Notification>>(
                {
                    client.get(
                            "notifications",
                            parameter
                    )
                },
                {
                    client.getSerializer().fromJson(it, Notification::class.java)
                }
        ).toPageable()
    }

    // GET /api/v1/notifications/:id
    fun getNotification(id: Long): MastodonRequest<Notification> {
        return MastodonRequest<Notification>(
                {
                    client.get("notifications/$id")
                },
                {
                    client.getSerializer().fromJson(it, Notification::class.java)
                }
        )
    }

    //  POST /api/v1/notifications/clear
    @Throws(JMARequestException::class)
    fun clearNotifications() {
        val response = client.post("notifications/clear",
                emptyRequestBody()
        )
        if (!response.isSuccessful) {
            throw JMARequestException(response)
        }
    }
}
