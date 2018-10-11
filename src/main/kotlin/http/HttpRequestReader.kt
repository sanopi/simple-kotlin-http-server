package http

import utils.Constants.Companion.HTTP_VERSION
import utils.Constants.Companion.PUBLIC_DIRECTORY
import utils.Constants.Companion.WHITE_SPACE
import utils.Logger
import java.io.File

/**
 * @author TomohiroSano
 */
class HttpRequestReader {

    private companion object {
        val logger: Logger = Logger(HttpRequestReader::class.simpleName!!)
        const val INDEX_FILE_NAME: String = "index.html"
    }

    fun validateRequestLine(requestLine: String?): HttpResponseStatus? {
        if (requestLine == null) {
            logger.warn("request line is null: $requestLine")
            return HttpResponseStatus.BAD_REQUEST
        }
        val splitLine = requestLine.split(WHITE_SPACE)
        return when {
            splitLine.size != 3 -> {
                logger.warn("request line does not consist of 3 items: $requestLine")
                HttpResponseStatus.BAD_REQUEST
            }
            splitLine[0] != HttpRequestMethod.GET.toString() -> {
                logger.warn("http method is not supported: ${splitLine[1]}")
                HttpResponseStatus.NOT_IMPLEMENTED
            }
            splitLine[2] != HTTP_VERSION -> {
                logger.warn("http version is not supported: ${splitLine[2]}")
                HttpResponseStatus.HTTP_VERSION_NOT_SUPPORTED
            }
            else -> null
        }
    }

    fun generateResponseBy(requestPath: String): HttpResponse {
        val file = File(PUBLIC_DIRECTORY + requestPath)

        return if (isDirectoryTraversalAttack(file)) {
            HttpResponse.createErrorResponse(HttpResponseStatus.FORBIDDEN)
        } else if (!file.exists()) {
            HttpResponse.createErrorResponse(HttpResponseStatus.NOT_FOUND)
        } else if (file.isDirectory) {
            generateDirectoryRequestedResponse(file)
        } else {
            val contentType = MediaType.values().firstOrNull { it.extension == file.extension } ?: MediaType.OCTET_STREAM
            HttpResponse(HttpResponseStatus.OK , file.readBytes(), contentType)
        }
    }

    private fun isDirectoryTraversalAttack(file: File) = !file.canonicalPath.startsWith(File(PUBLIC_DIRECTORY).canonicalPath)

    private fun generateDirectoryRequestedResponse(file: File): HttpResponse {
        if (!file.isDirectory) throw IllegalArgumentException("file must be directory, file.isDirectory: ${file.isDirectory}")

        val files = file.listFiles()
        val indexFile = files.firstOrNull { it.name == (INDEX_FILE_NAME) }
        return if (indexFile != null) {
            HttpResponse(HttpResponseStatus.OK, indexFile.readBytes(), MediaType.HTML)
        } else {
            HttpResponse(HttpResponseStatus.OK, generateFileNameListPage(file.name, files), MediaType.HTML)
        }
    }

    private fun generateFileNameListPage(dirName: String, files: Array<File>): ByteArray {
        return """
                |<!DOCTYPE html>
                |<html lang="en">
                |<head>
                |    <meta charset="UTF-8">
                |    <title>$dirName</title>
                |</head>
                |<body>
                |<h1>Auto generated Index page of $dirName</h1>
                |<h2>Contents of Directory is listed below...</h2>
                |<ul>
                |${files.map { "<li><a href=\"$dirName/${it.name}\">${it.name}</a></li>" }.reduce { acc, name -> acc + name }}
                |</ul>
                |</body>
                |</html>
            """.trimMargin().toByteArray()
    }
}
