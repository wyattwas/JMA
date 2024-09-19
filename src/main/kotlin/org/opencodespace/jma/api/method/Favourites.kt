package org.opencodespace.jma.api.method

import org.opencodespace.jma.MastodonClient
import org.opencodespace.jma.MastodonRequest
import org.opencodespace.jma.api.Pageable
import org.opencodespace.jma.api.Range
import org.opencodespace.jma.api.entity.Status

/**
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#favourites
 */
class Favourites(private val client: MastodonClient) {

    //  GET /api/v1/favourites
    @JvmOverloads
    fun getFavourites(range: Range = Range()): MastodonRequest<Pageable<Status>> {
        return MastodonRequest<Pageable<Status>>(
                {
                    client.get("favourites", range.toParameter())
                },
                {
                    client.getSerializer().fromJson(it, Status::class.java)
                }
        ).toPageable()
    }
}
