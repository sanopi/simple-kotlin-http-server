import http.HeaderFieldName
import http.HttpResponse
import http.HttpResponseStatus
import http.MediaType
import utils.Constants.Companion.CHARACTER_SET
import utils.Logger
import java.io.IOException
import java.net.ServerSocket
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.system.exitProcess

/**
 * @author TomohiroSano
 */

private const val PORT: Int = 8000
val logger: Logger = Logger("Root")

fun main(args: Array<String>) {
    try {
        ServerSocket(PORT).use { serverSocket ->
            logger.info("listening HTTP request on localhost port ${serverSocket.localPort} ...")
            while (true) {
                respondToClient(serverSocket)
            }
        }
    } catch (e: Exception) {
        logger.error("Something wrong occurred. Killing this HTTP server...: ${e.message}")
        exitProcess(-1)
    }
}

private fun respondToClient(serverSocket: ServerSocket) {
    try {
        serverSocket.accept().use { socket ->
//            socket.getInputStream().bufferedReader().use { reader ->
                socket.getOutputStream().bufferedWriter().use { writer ->
                    val body = "<h1>Hello, Client<h1/>"
                    val response = HttpResponse(HttpResponseStatus.OK, body)
                    val date = ZonedDateTime.now(ZoneOffset.UTC)
                    response.addHeaderField(HeaderFieldName.DATE, date.format(DateTimeFormatter.RFC_1123_DATE_TIME))
                    response.addHeaderField(HeaderFieldName.CONTENT_TYPE, MediaType.HTML.toString())
                    response.addHeaderField(HeaderFieldName.CONTENT_LENGTH, body.toByteArray(charset(CHARACTER_SET)).size.toString())
                    writer.write(response.constructResponse())
                }
//            }
        }
    } catch (e: IOException) {
        logger.error("Could not respond to the client: ${e.message}")
    }
}
