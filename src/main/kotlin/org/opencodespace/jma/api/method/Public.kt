package org.opencodespace.jma.api.method

import org.opencodespace.jma.MastodonClient
import org.opencodespace.jma.MastodonRequest
import org.opencodespace.jma.Parameter
import org.opencodespace.jma.api.Pageable
import org.opencodespace.jma.api.Range
import org.opencodespace.jma.api.entity.Instance
import org.opencodespace.jma.api.entity.Results
import org.opencodespace.jma.api.entity.Status

class Public(private val client: MastodonClient) {
    /**
     * GET /api/v1/instance
     * @see https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#instances
     */
    fun getInstance(): MastodonRequest<Instance> {
        return MastodonRequest(
                {
                    client.get("instance")
                },
                { json ->
                    client.getSerializer().fromJson(json, Instance::class.java)
                }
        )
    }

    /**
     * GET /api/v1/search
     * q: The search query
     * resolve: Whether to resolve non-local accounts
     * @see https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#search
     */
    @JvmOverloads
    fun getSearch(query: String, resolve: Boolean = false): MastodonRequest<Results> {
        return MastodonRequest<Results>(
                {
                    client.get(
                            "search",
                            Parameter().apply {
                                append("q", query)
                                if (resolve) {
                                    append("resolve", resolve)
                                }
                            }
                    )
                },
                {
                    client.getSerializer().fromJson(it, Results::class.java)
                }
        )
    }

    /**
     *  GET /api/v1/timelines/public
     * @see https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#timelines
     */
    private fun getPublic(local: Boolean, range: Range): MastodonRequest<Pageable<Status>> {
        val parameter = range.toParameter()
        if (local) {
            parameter.append("local", local)
        }
        return MastodonRequest<Pageable<Status>>(
                {
                    client.get("timelines/public", parameter)
                },
                {
                    client.getSerializer().fromJson(it, Status::class.java)
                }
        ).toPageable()
    }

    @JvmOverloads
    fun getLocalPublic(range: Range = Range()) = getPublic(true, range)

    @JvmOverloads
    fun getFederatedPublic(range: Range = Range()) = getPublic(false, range)

    /**
     * GET /api/v1/timelines/tag/:tag
     * @see https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#timelines
     */
    private fun getTag(tag: String, local: Boolean, range: Range): MastodonRequest<Pageable<Status>> {
        val parameter = range.toParameter()
        if (local) {
            parameter.append("local", local)
        }
        return MastodonRequest<Pageable<Status>>(
                {
                    client.get(
                            "timelines/tag/$tag",
                            parameter
                    )
                },
                {
                    client.getSerializer().fromJson(it, Status::class.java)
                }
        ).toPageable()
    }

    @JvmOverloads
    fun getLocalTag(tag: String, range: Range = Range()) = getTag(tag, true, range)

    @JvmOverloads
    fun getFederatedTag(tag: String, range: Range = Range()) = getTag(tag, false, range)
}