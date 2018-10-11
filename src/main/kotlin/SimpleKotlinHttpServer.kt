import server.ServerThread
import utils.Logger
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
                val socket = serverSocket.accept()
                Thread(ServerThread(socket)).start()
            }
        }
    } catch (e: Exception) {
        logger.error("Something wrong occurred. Killing this HTTP server...: ${e.message}")
        e.printStackTrace()
        exitProcess(-1)
    }
}
