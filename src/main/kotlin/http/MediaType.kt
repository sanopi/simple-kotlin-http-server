package http

import utils.Constants.Companion.CHARACTER_SET

/**
 * @author TomohiroSano
 */
enum class MediaType(private val type: Type, private val subType: SubType, val extension: String? ) {

    HTML(Type.TEXT, SubType.HTML, "html"),
    CSS(Type.TEXT, SubType.CSS, "css"),
    JAVASCRIPT(Type.TEXT, SubType.JAVASCRIPT, "js"),
    PLAIN(Type.TEXT, SubType.PLAIN, "txt"),
    JPEG(Type.IMAGE, SubType.JPEG, "jpeg"),
    JPG(Type.IMAGE, SubType.JPEG, "jpg"),
    PNG(Type.IMAGE, SubType.PNG, "png"),
    GIF(Type.IMAGE, SubType.GIF, "gif"),
    OCTET_STREAM(Type.APPLICATION, SubType.OCTET_STREAM, null);

    override fun toString(): String {
        return if (type == Type.TEXT) {
            "$type/$subType;charset=$CHARACTER_SET"
        } else {
            "$type/$subType"
        }
    }

    // ========== inner enum ==========
    private enum class Type(val token: String) {

        TEXT("text"),
        IMAGE("image"),
        APPLICATION("application");

        override fun toString(): String {
            return token
        }
    }

    private enum class SubType(val token: String) {

        HTML("html"),
        CSS("css"),
        JAVASCRIPT("javascript"),
        PLAIN("plain"),
        JPEG("jpeg"),
        PNG("png"),
        GIF("gif"),
        OCTET_STREAM("octet-stream");

        override fun toString(): String {
            return token
        }
    }
}
