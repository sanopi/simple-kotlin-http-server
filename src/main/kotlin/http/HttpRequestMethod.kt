package http

/**
 * @author TomohiroSano
 */
enum class HttpRequestMethod(val method: String) {

    GET("GET");

    override fun toString(): String {
        return method
    }
}
