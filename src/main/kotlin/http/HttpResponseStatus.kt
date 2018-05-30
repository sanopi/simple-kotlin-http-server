package http

/**
 * @author TomohiroSano
 */
enum class HttpResponseStatus(val statusCode: Int, val reasonPhrase: String) {
    OK(200, "OK");

    fun getStatus() = "$statusCode $reasonPhrase"
}
