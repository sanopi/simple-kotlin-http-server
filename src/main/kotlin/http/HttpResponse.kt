package http

import utils.Constants.Companion.CRLF
import utils.Constants.Companion.HTTP_VERSION

/**
 * @author TomohiroSano
 */
class HttpResponse(responseStatus: HttpResponseStatus, private val responseBody: String) {

    // ========== Field ==========
    private val statusLine: String = "$HTTP_VERSION ${responseStatus.getStatus()}$CRLF"

    private val headerFields: MutableList<HttpResponseHeaderField> = mutableListOf()

    // ========== Function ==========
    fun addHeaderField(fieldName: HeaderFieldName, fieldValue: String) {
        headerFields.add(HttpResponseHeaderField(fieldName, fieldValue))
    }

    fun constructResponse(): String {
        val headerLines = headerFields.map { it.headerLine }.reduce { acc, headerLine -> acc + headerLine }
        return statusLine + headerLines + CRLF + responseBody
    }
}
