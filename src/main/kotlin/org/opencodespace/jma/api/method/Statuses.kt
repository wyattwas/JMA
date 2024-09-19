package org.opencodespace.jma.api.method

import org.opencodespace.jma.MastodonClient
import org.opencodespace.jma.MastodonRequest
import org.opencodespace.jma.Parameter
import org.opencodespace.jma.api.Pageable
import org.opencodespace.jma.api.Range
import org.opencodespace.jma.api.entity.*
import org.opencodespace.jma.api.exception.JMARequestException
import org.opencodespace.jma.extension.emptyRequestBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

/**
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#statuses
 */
class Statuses(private val client: MastodonClient) {

    //  GET /api/v1/statuses/:id
    @Throws(JMARequestException::class)
    fun getStatus(statusId: Long): MastodonRequest<Status> {
        return MastodonRequest<Status>(
                {
                    client.get("statuses/$statusId")
                },
                {
                    client.getSerializer().fromJson(it, Status::class.java)
                }
        )
    }

    //  GET /api/v1/statuses/:id/context
    @Throws(JMARequestException::class)
    fun getContext(statusId: Long): MastodonRequest<Context> {
        return MastodonRequest<Context>(
                {
                    client.get("statuses/$statusId/context")
                },
                {
                    client.getSerializer().fromJson(it, Context::class.java)
                }
        )
    }

    //  GET /api/v1/statuses/:id/card
    @Throws(JMARequestException::class)
    fun getCard(statusId: Long): MastodonRequest<Card> {
        return MastodonRequest<Card>(
                {
                    client.get("statuses/$statusId/card")
                },
                {
                    client.getSerializer().fromJson(it, Card::class.java)
                }
        )
    }

    //  GET /api/v1/reblogged_by
    @JvmOverloads
    @Throws(JMARequestException::class)
    fun getRebloggedBy(statusId: Long, range: Range = Range()): MastodonRequest<Pageable<Account>> {
        return MastodonRequest<Pageable<Account>>(
                {
                    client.get(
                            "statuses/$statusId/reblogged_by",
                            range.toParameter()
                    )
                },
                {
                    client.getSerializer().fromJson(it, Account::class.java)
                }
        ).toPageable()
    }

    //  GET /api/v1/favourited_by
    @JvmOverloads
    @Throws(JMARequestException::class)
    fun getFavouritedBy(statusId: Long, range: Range = Range()): MastodonRequest<Pageable<Account>> {
        return MastodonRequest<Pageable<Account>>(
                {
                    client.get(
                            "statuses/$statusId/favourited_by",
                            range.toParameter()
                    )
                },
                {
                    client.getSerializer().fromJson(it, Account::class.java)
                }
        ).toPageable()
    }

    /**
     * POST /api/v1/status
     * status: The text of the status
     * in_reply_to_id (optional): local ID of the status you want to reply to
     * media_ids (optional): array of media IDs to attach to the status (maximum 4)
     * sensitive (optional): set this to mark the media of the status as NSFW
     * spoiler_text (optional): text to be shown as a warning before the actual content
     * visibility (optional): either "direct", "private", "unlisted" or "public"
     */
    @JvmOverloads
    @Throws(JMARequestException::class)
    fun postStatus(
            status: String,
            inReplyToId: Long?,
            mediaIds: List<Long>?,
            sensitive: Boolean,
            spoilerText: String?,
            visibility: Status.Visibility = Status.Visibility.Public
    ): MastodonRequest<Status> {
        val parameters = Parameter().apply {
            append("status", status)
            inReplyToId?.let {
                append("in_reply_to_id", it)
            }
            mediaIds?.let {
                append("media_ids", it)
            }
            append("sensitive", sensitive)
            spoilerText?.let {
                append("spoiler_text", it)
            }
            append("visibility", visibility.value)
        }.build()

        return MastodonRequest<Status>(
                {
                    client.post("statuses",
                            RequestBody.create(
                                "application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull(),
                                    parameters
                            ))
                },
                {
                    client.getSerializer().fromJson(it, Status::class.java)
                }
        )
    }

    //  DELETE /api/v1/statuses/:id
    @Throws(JMARequestException::class)
    fun deleteStatus(statusId: Long) {
        val response = client.delete("statuses/$statusId")
        if (!response.isSuccessful) {
            throw JMARequestException(response)
        }
    }

    //  POST /api/v1/statuses/:id/reblog
    @Throws(JMARequestException::class)
    fun postReblog(statusId: Long): MastodonRequest<Status> {
        return MastodonRequest<Status>(
                {
                    client.post("statuses/$statusId/reblog", emptyRequestBody())
                },
                {
                    client.getSerializer().fromJson(it, Status::class.java)
                }
        )
    }

    //  POST /api/v1/statuses/:id/unreblog
    @Throws(JMARequestException::class)
    fun postUnreblog(statusId: Long): MastodonRequest<Status> {
        return MastodonRequest<Status>(
                {
                    client.post("statuses/$statusId/unreblog", emptyRequestBody())
                },
                {
                    client.getSerializer().fromJson(it, Status::class.java)
                }
        )
    }

    //  POST /api/v1/statuses/:id/favourite
    @Throws(JMARequestException::class)
    fun postFavourite(statusId: Long): MastodonRequest<Status> {
        return MastodonRequest<Status>(
                {
                    client.post("statuses/$statusId/favourite", emptyRequestBody())
                },
                {
                    client.getSerializer().fromJson(it, Status::class.java)
                }
        )
    }

    //  POST /api/v1/statuses/:id/unfavourite
    @Throws(JMARequestException::class)
    fun postUnfavourite(statusId: Long): MastodonRequest<Status> {
        return MastodonRequest<Status>(
                {
                    client.post("statuses/$statusId/unfavourite", emptyRequestBody())
                },
                {
                    client.getSerializer().fromJson(it, Status::class.java)
                }
        )
    }
}
