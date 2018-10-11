package http

import utils.Constants

/**
 * @author TomohiroSano
 */
class HttpResponseHeaderField(fieldName: HttpResponseHeaderFieldName, fieldValue: String) {

    val headerLine = "${fieldName.token}: $fieldValue${Constants.CRLF}"
}
