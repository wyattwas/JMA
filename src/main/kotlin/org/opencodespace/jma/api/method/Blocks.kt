package org.opencodespace.jma.api.method

import org.opencodespace.jma.MastodonClient
import org.opencodespace.jma.MastodonRequest
import org.opencodespace.jma.api.Pageable
import org.opencodespace.jma.api.Range
import org.opencodespace.jma.api.entity.Account

/**
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#blocks
 */
class Blocks(private val client: MastodonClient) {

    //  GET /api/v1/blocks
    @JvmOverloads
    fun getBlocks(range: Range = Range()):MastodonRequest<Pageable<Account>> {
        return MastodonRequest<Pageable<Account>>(
                {
                    client.get("blocks", range.toParameter())
                },
                {
                    client.getSerializer().fromJson(it, Account::class.java)
                }
        ).toPageable()
    }
}
