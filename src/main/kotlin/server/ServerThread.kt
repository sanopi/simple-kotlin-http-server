package server

import http.HttpRequestReader
import http.HttpResponse
import http.HttpResponseStatus
import utils.Constants
import utils.Logger
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.IOException
import java.net.Socket

/**
 * @created_date 2018/10/11
 * @author TomohiroSano
 */
class ServerThread(private val socket: Socket) : Runnable {

    private companion object {
        private val logger = Logger(ServerThread::class.simpleName!!)
    }

    override fun run() {
        try {
            socket.use { socket ->
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
            httpRequestReader.generateResponseBy(statusLine!!.split(Constants.WHITE_SPACE)[1])
        }
        out.write(response.constructResponseBytes())
    }
}
