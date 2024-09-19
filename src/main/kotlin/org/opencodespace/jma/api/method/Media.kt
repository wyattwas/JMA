package org.opencodespace.jma.api.method

import org.opencodespace.jma.MastodonClient
import org.opencodespace.jma.MastodonRequest
import org.opencodespace.jma.api.entity.Attachment
import okhttp3.MultipartBody

/**
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#media
 */
class Media(private val client: MastodonClient) {
    //  POST /api/v1/media
    fun postMedia(file: MultipartBody.Part): MastodonRequest<Attachment> {
        val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(file)
                .build()
        return MastodonRequest<Attachment>(
                {
                    client.post("media", requestBody)
                },
                {
                    client.getSerializer().fromJson(it, Attachment::class.java)
                }
        )
    }
}
