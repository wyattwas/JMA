package org.opencodespace.jma.api.method

import org.opencodespace.jma.MastodonClient
import org.opencodespace.jma.MastodonRequest
import org.opencodespace.jma.api.Pageable
import org.opencodespace.jma.api.Range
import org.opencodespace.jma.api.entity.Status
import org.opencodespace.jma.api.exception.JMARequestException

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#timelines
 */
class Timelines(private val client: MastodonClient) {
    //  GET /api/v1/timelines/home
    @JvmOverloads
    @Throws(JMARequestException::class)
    fun getHome(range: Range = Range()): MastodonRequest<Pageable<Status>> {
        return MastodonRequest<Pageable<Status>>(
                {
                    client.get(
                            "timelines/home",
                            range.toParameter()
                    )
                },
                {
                    client.getSerializer().fromJson(it, Status::class.java)
                }
        ).toPageable()
    }
}
