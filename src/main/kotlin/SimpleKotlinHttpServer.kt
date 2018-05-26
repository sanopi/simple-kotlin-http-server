import utils.Constants.Companion.PORT
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket

/**
 * @author TomohiroSano
 */


fun main(args: Array<String>) {
    ServerSocket().use { serverSocket ->
        serverSocket.bind(InetSocketAddress(InetAddress.getLoopbackAddress(), PORT))
        println("listening HTTP request on ${serverSocket.inetAddress} port ${serverSocket.localPort} ...")
        val socket = serverSocket.accept()
        println("Hello Request!")
    }
}
