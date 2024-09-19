package org.opencodespace.jma.api.entity

import com.google.gson.Gson
import org.opencodespace.jma.testtool.AssetsUtil
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBe
import org.junit.Test

class AttachmentTest {
    @Test
    fun deserialize() {
        val json = AssetsUtil.readFromAssets("attachment.json")
        val status: Attachment = Gson().fromJson(json, Attachment::class.java)
        status.id shouldEqualTo 10L
        status.url shouldEqualTo "youtube"
        status.remoteUrl shouldNotBe null
        status.previewUrl shouldEqualTo "preview"
        status.textUrl shouldNotBe null
        status.type shouldEqualTo Attachment.Type.Video.value
    }
}
