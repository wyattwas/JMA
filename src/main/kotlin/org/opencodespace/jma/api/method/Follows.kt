package org.opencodespace.jma.api.method

import org.opencodespace.jma.MastodonClient
import org.opencodespace.jma.MastodonRequest
import org.opencodespace.jma.Parameter
import org.opencodespace.jma.api.entity.Account
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

/**
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#follows
 */
class Follows(private val client: MastodonClient) {
    /**
     * POST /api/v1/follows
     * @param uri: username@domain of the person you want to follow
     */
    fun postRemoteFollow(uri: String): MastodonRequest<Account> {
        val parameters = Parameter()
                .append("uri", uri)
                .build()
        return MastodonRequest<Account>(
                {
                    client.post("follows",
                            RequestBody.create(
                                "application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull(),
                                    parameters
                            )
                    )
                },
                {
                    client.getSerializer().fromJson(it, Account::class.java)
                }
        )
    }
}
