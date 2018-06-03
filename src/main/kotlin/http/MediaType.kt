package http

import utils.Constants.Companion.CHARACTER_SET

/**
 * @author TomohiroSano
 */
enum class MediaType(private val type: Type, private val subType: SubType) {

    HTML(Type.TEXT, SubType.HTML);

    override fun toString(): String {
        return if (type.equals(Type.TEXT)) {
            "$type/$subType;charset=$CHARACTER_SET"
        } else {
            "$type/$subType"
        }
    }

    // ========== inner enum ==========
    private enum class Type(val token: String) {

        TEXT("text");

        override fun toString(): String {
            return token
        }
    }

    private enum class SubType(val token: String) {

        HTML("html");

        override fun toString(): String {
            return token
        }
    }
}
