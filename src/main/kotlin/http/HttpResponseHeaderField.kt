package http

import utils.Constants

/**
 * @author TomohiroSano
 */
class HttpResponseHeaderField(val fieldName: HeaderFieldName, private val fieldValue: String) {

    val headerLine = "${fieldName.token}: $fieldValue${Constants.CRLF}"
}

enum class HeaderFieldName(val token: String) {

    DATE("Date"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length");
}
