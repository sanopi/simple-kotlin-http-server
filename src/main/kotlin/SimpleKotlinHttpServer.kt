import utils.Logger
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import kotlin.system.exitProcess

/**
 * @author TomohiroSano
 */

const val PORT: Int = 8000
val logger: Logger = Logger("parent")

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
    // OutputStream and Socket is also closed by closing BufferedWriter.
    try {
        serverSocket.accept().getOutputStream().bufferedWriter().use { writer ->
            writer.write("Hello Request!")
        }
    } catch (e: IOException) {
        logger.error("Could not respond to the client.")
        e.printStackTrace()
    }
}
