import utils.Logger
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import kotlin.system.exitProcess

/**
 * @author TomohiroSano
 */

private const val PORT: Int = 8000
private const val CRLF: String = "\r\n"
val logger: Logger = Logger("Root")

fun main(args: Array<String>) {
    try {
        ServerSocket().use { serverSocket ->
            serverSocket.bind(InetSocketAddress(InetAddress.getLoopbackAddress(), PORT))
            logger.info("listening HTTP request on ${serverSocket.inetAddress.hostName} port ${serverSocket.localPort} ...")
            while (true) {
                respondToClient(serverSocket)
            }
        }
    } catch (e: Exception) {
        logger.error("Something wrong occurred. Killing this HTTP server...")
        exitProcess(-1)
    }
}

private fun respondToClient(serverSocket: ServerSocket) {
    try {
        serverSocket.accept().use { socket ->
            socket.getInputStream().bufferedReader().use { reader ->
                socket.getOutputStream().bufferedWriter().use { writer ->
                    while (reader.ready()) {
                        writer.write(reader.readLine() + CRLF)
                    }
                }
            }
        }
    } catch (e: IOException) {
        logger.error("Could not respond to the client.")
        e.printStackTrace()
    }
}
