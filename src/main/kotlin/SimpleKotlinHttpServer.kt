import http.HttpRequestReader
import http.HttpResponse
import http.HttpResponseStatus
import utils.Constants.Companion.WHITE_SPACE
import utils.Logger
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.IOException
import java.net.ServerSocket
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
        e.printStackTrace()
        exitProcess(-1)
    }
}

private fun respondToClient(serverSocket: ServerSocket) {
    try {
        serverSocket.accept().use { socket ->
            socket.getInputStream().bufferedReader().use { reader ->
                socket.getOutputStream().buffered().use { out ->
                    writeResponseFromRequest(reader, out)
                }
            }
        }
    } catch (e: IOException) {
        logger.error("Could not respond to the client: ${e.message}")
    }
}

private fun writeResponseFromRequest(reader: BufferedReader, out: BufferedOutputStream) {
    val statusLine: String? = reader.readLine()
    val httpRequestReader = HttpRequestReader()
    logger.info("status line: $statusLine")
    val errorStatus: HttpResponseStatus? = httpRequestReader.validateRequestLine(statusLine)
    val response = if (errorStatus != null) {
        HttpResponse.createErrorResponse(errorStatus)
    } else {
        httpRequestReader.generateResponseBy(statusLine!!.split(WHITE_SPACE)[1])
    }
    out.write(response.constructResponseBytes())
}
