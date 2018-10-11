package http

import utils.Constants.Companion.CRLF
import utils.Constants.Companion.HTTP_VERSION
import utils.Logger
import java.io.File
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * @author TomohiroSano
 */
class HttpResponse(responseStatus: HttpResponseStatus, private val responseBody: ByteArray, contentType: MediaType) {

    // ========== companion ==========
    companion object {

        private val logger = Logger(HttpResponse::class.simpleName!!)

        fun createErrorResponse(status: HttpResponseStatus): HttpResponse {
            try {
                val file = File("src/main/resource/error/${status.statusCode}.html")
                val response = HttpResponse(status, file.readBytes(), MediaType.HTML)
                return response
            } catch (e: Exception) {
                logger.error("cannot read error html: ${e.message}")
                val body = """
                |<!DOCTYPE html>
                |<html lang="en">
                |<head>
                |    <meta charset="UTF-8">
                |    <title>500 Internal Server Error</title>
                |</head>
                |<body>
                |<h1>500 Internal Server Error</h1>
                |</body>
                |</html>
            """.trimMargin().toByteArray()
                return HttpResponse(status, body, MediaType.HTML)
            }
        }
    }

    // ========== Property ==========
    private val statusLine: String = "$HTTP_VERSION ${responseStatus.getStatus()}$CRLF"

    private val headerFields: MutableList<HttpResponseHeaderField> = mutableListOf()

    // ========== init/constructor ==========
    init {
        val date = ZonedDateTime.now(ZoneOffset.UTC)
        addHeaderField(HttpResponseHeaderFieldName.DATE, date.format(DateTimeFormatter.RFC_1123_DATE_TIME))
        addHeaderField(HttpResponseHeaderFieldName.CONTENT_LENGTH, responseBody.size.toString())
        addHeaderField(HttpResponseHeaderFieldName.CONTENT_TYPE, contentType.toString())
    }

    // ========== Function ==========
    private fun addHeaderField(fieldName: HttpResponseHeaderFieldName, fieldValue: String) {
        headerFields.add(HttpResponseHeaderField(fieldName, fieldValue))
    }

    fun constructResponseBytes(): ByteArray {
        val headerLines = headerFields.map { it.headerLine }.reduce { acc, headerLine -> acc + headerLine }
        return (statusLine + headerLines + CRLF).toByteArray().plus(responseBody)
    }
}
