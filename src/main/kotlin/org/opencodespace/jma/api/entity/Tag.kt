package org.opencodespace.jma.api.entity

import com.google.gson.annotations.SerializedName

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#tag
 */
class Tag(
        @SerializedName("name")
        val name: String = "",

        @SerializedName("url")
        val url: String = "") {
}
