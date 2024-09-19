package org.opencodespace.jma.api.method

import org.opencodespace.jma.MastodonClient
import org.opencodespace.jma.MastodonRequest
import org.opencodespace.jma.Parameter
import org.opencodespace.jma.api.Pageable
import org.opencodespace.jma.api.Range
import org.opencodespace.jma.api.entity.Report
import org.opencodespace.jma.api.exception.JMARequestException
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

/**
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#reports
 */
class Reports(private val client: MastodonClient) {
    // GET /api/v1/reports
    @JvmOverloads
    @Throws(JMARequestException::class)
    fun getReports(range: Range = Range()): MastodonRequest<Pageable<Report>> {
        return MastodonRequest<Pageable<Report>>(
                {
                    client.get(
                            "reports",
                            range.toParameter()
                    )
                },
                {
                    client.getSerializer().fromJson(it, Report::class.java)
                }
        ).toPageable()
    }

    /**
     * POST /api/v1/reports
     * account_id: The ID of the account to report
     * status_ids: The IDs of statuses to report (can be an array)
     * comment: A comment to associate with the report.
     */
    @Throws(JMARequestException::class)
    fun postReport(accountId: Long, statusId: Long, comment: String): MastodonRequest<Report> {
        val parameters = Parameter().apply {
            append("account_id", accountId)
            append("status_ids", statusId)
            append("comment", comment)
        }.build()
        return MastodonRequest<Report>(
                {
                    client.post("reports",
                            RequestBody.create(
                                "application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull(),
                                    parameters
                            ))
                },
                {
                    client.getSerializer().fromJson(it, Report::class.java)
                }
        )
    }
}
