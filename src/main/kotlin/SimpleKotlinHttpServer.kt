import utils.Constants.Companion.PORT
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import kotlin.system.exitProcess

/**
 * @author TomohiroSano
 */


fun main(args: Array<String>) {
    try {
        ServerSocket().use { serverSocket ->
            serverSocket.bind(InetSocketAddress(InetAddress.getLoopbackAddress(), PORT))
            println("listening HTTP request on ${serverSocket.inetAddress.hostName} port ${serverSocket.localPort} ...")
            while (true) {
                respondToClient(serverSocket)
            }
        }
    } catch (e: Exception) {
        println("Something wrong occurred. Killing this HTTP server...")
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
        // TODO create logger class
        println("Could not respond to the client.")
        e.printStackTrace()
    }
}
