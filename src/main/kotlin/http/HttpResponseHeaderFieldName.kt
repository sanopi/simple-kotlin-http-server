package http

/**
 * @created_date 2018/10/11
 * @author TomohiroSano
 */
enum class HttpResponseHeaderFieldName(val token: String) {

    DATE("Date"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length");
}

