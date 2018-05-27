package utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @author TomohiroSano
 */
class Logger(private val loggerName: String) {

    fun info(msg: String) {
        println("\u001b[32m[$loggerName]\u001b[0m[${Thread.currentThread().name}] ${getFormatedDateTime()}\t$msg\r\n")
    }

    fun warn(msg: String) {
        println("\u001b[33m[$loggerName]\u001b[0m[${Thread.currentThread().name}] ${getFormatedDateTime()}\t$msg\r\n")
    }

    fun error(msg: String) {
        println("\u001b[31m[$loggerName]\u001b[0m[${Thread.currentThread().name}] ${getFormatedDateTime()}\t$msg\r\n")
    }

    private fun getFormatedDateTime() = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}
