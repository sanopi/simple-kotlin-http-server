package http

/**
 * @author TomohiroSano
 */
enum class HttpResponseStatus(val statusCode: Int, val reasonPhrase: String) {
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported");

    fun getStatus() = "$statusCode $reasonPhrase"
}
