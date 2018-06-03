package http

import utils.Constants.Companion.CRLF
import utils.Constants.Companion.HTTP_VERSION
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * @author TomohiroSano
 */
class HttpResponse(responseStatus: HttpResponseStatus, private val responseBody: String) {

    // ========== Field ==========
    private val statusLine: String = "$HTTP_VERSION ${responseStatus.getStatus()}$CRLF"

    private val headerFields: MutableList<HttpResponseHeaderField> = mutableListOf()

    // ========== init ==========
    init {
        val date = ZonedDateTime.now(ZoneOffset.UTC)
        addHeaderField(HeaderFieldName.DATE, date.format(DateTimeFormatter.RFC_1123_DATE_TIME))
    }

    // ========== Function ==========
    fun addHeaderField(fieldName: HeaderFieldName, fieldValue: String) {
        headerFields.add(HttpResponseHeaderField(fieldName, fieldValue))
    }

    fun buildResponse(): String {
        val headerLines = headerFields.map { it.headerLine }.reduce { acc, headerLine -> acc + headerLine }
        return statusLine + headerLines + CRLF + responseBody
    }
}
