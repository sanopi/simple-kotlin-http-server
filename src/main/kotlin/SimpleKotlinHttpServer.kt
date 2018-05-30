import http.HeaderFieldName
import http.HttpResponse
import http.HttpResponseStatus
import utils.Constants.Companion.CRLF
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
            socket.getInputStream().bufferedReader().use { reader ->
                socket.getOutputStream().bufferedWriter().use { writer ->
                    val bodyBuilder = StringBuilder()
                    while (reader.ready()) {
                        bodyBuilder.append(reader.readLine() + CRLF)
                    }
                    val response = HttpResponse(HttpResponseStatus.OK, bodyBuilder.toString())
                    val date = ZonedDateTime.now(ZoneOffset.UTC)
                    response.addHeaderField(HeaderFieldName.DATE, date.format(DateTimeFormatter.RFC_1123_DATE_TIME))
                    writer.write(response.constructResponse())
                }
            }
        }
    } catch (e: IOException) {
        logger.error("Could not respond to the client: ${e.message}")
    }
}
