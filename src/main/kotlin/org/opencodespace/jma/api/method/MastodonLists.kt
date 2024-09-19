package org.opencodespace.jma.api.method

import org.opencodespace.jma.MastodonClient
import org.opencodespace.jma.MastodonRequest
import org.opencodespace.jma.api.Pageable
import org.opencodespace.jma.api.Range
import org.opencodespace.jma.api.entity.MastodonList
import org.opencodespace.jma.api.entity.Status
import org.opencodespace.jma.api.exception.JMARequestException

class MastodonLists(private val client: MastodonClient) {

    // GET /api/v1/lists
    fun getLists(): MastodonRequest<Pageable<MastodonList>> {
        return MastodonRequest<Pageable<MastodonList>>(
                {
                    client.get(
                            "lists"
                    )
                },
                {
                    client.getSerializer().fromJson(it, MastodonList::class.java)
                }
        ).toPageable()
    }

    //GET /api/v1/timelines/list/:list_id
    @Throws(JMARequestException::class)
    fun getListTimeLine(listID: Long, range: Range = Range()): MastodonRequest<Pageable<Status>> {
        return MastodonRequest<Pageable<Status>>(
                {
                    client.get(
                            "timelines/list/$listID",
                            range.toParameter()
                    )
                },
                {
                    client.getSerializer().fromJson(it, Status::class.java)
                }
        ).toPageable()
    }
}