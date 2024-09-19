package org.opencodespace.jma.api

import org.amshove.kluent.shouldEqualTo
import org.junit.Test

import org.junit.Assert.*

class LinkTest {
    @Test
    fun parse() {
        // both
        run {
            val link = Link.parse(
                    """
<https://mstdn.jp/api/v1/timelines/public?limit=20&local=true&max_id=1552>; rel="next", <https://mstdn.jp/api/v1/timelines/public?limit=20&local=true&since_id=105>; rel="prev"
"""
            )
            if (link == null) {
                fail()
                return
            }
            link.maxId shouldEqualTo 1552
            link.sinceId shouldEqualTo 105
        }

        // max
        run {
            val link = Link.parse(
                    """
<https://mstdn.jp/api/v1/timelines/public?limit=20&local=true&max_id=1553>; rel="next"
"""
            )
            if (link == null) {
                fail()
                return
            }
            link.maxId shouldEqualTo 1553
            link.sinceId shouldEqualTo 0
            link.prevPath shouldEqualTo ""
        }

        // since
        run {
            val link = Link.parse(
                    """
<https://mstdn.jp/api/v1/timelines/public?limit=20&local=true&since_id=105>; rel="prev"
"""
            )
            if (link == null) {
                fail()
                return
            }
            link.maxId shouldEqualTo 0
            link.nextPath shouldEqualTo ""
            link.sinceId shouldEqualTo 105
        }
    }
}