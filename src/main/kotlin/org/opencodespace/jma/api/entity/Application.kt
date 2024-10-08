package org.opencodespace.jma.api.entity

import com.google.gson.annotations.SerializedName

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#application
 */
class Application(
        @SerializedName("name")
        val name: String = "",

        @SerializedName("website")
        val website: String? = null) {
}
