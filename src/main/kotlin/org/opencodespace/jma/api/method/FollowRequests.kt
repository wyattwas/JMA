package org.opencodespace.jma.api.method

import org.opencodespace.jma.MastodonClient
import org.opencodespace.jma.MastodonRequest
import org.opencodespace.jma.api.Pageable
import org.opencodespace.jma.api.Range
import org.opencodespace.jma.api.entity.Account
import org.opencodespace.jma.api.exception.JMARequestException
import org.opencodespace.jma.extension.emptyRequestBody

/**
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#follow-requests
 */
class FollowRequests(private val client: MastodonClient) {
    // GET /api/v1/follow_requests
    @JvmOverloads
    fun getFollowRequests(range: Range = Range()): MastodonRequest<Pageable<Account>> {
        return MastodonRequest<Pageable<Account>>(
                { client.get("follow_requests", range.toParameter()) },
                {
                    client.getSerializer().fromJson(it, Account::class.java)
                }
        ).toPageable()
    }

    //  POST /api/v1/follow_requests/:id/authorize
    @Throws(JMARequestException::class)
    fun postAuthorize(accountId: Long) {
        val response = client.post("follow_requests/$accountId/authorize", emptyRequestBody())
        if (!response.isSuccessful) {
            throw JMARequestException(response)
        }
    }

    //  POST /api/v1/follow_requests/:id/reject
    @Throws(JMARequestException::class)
    fun postReject(accountId: Long) {
        val response = client.post("follow_requests/$accountId/reject", emptyRequestBody())
        if (!response.isSuccessful) {
            throw JMARequestException(response)
        }
    }
}
