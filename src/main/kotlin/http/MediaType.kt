package http

import utils.Constants.Companion.CHARACTER_SET

/**
 * @created_date 2018/05/31
 * @author TomohiroSano
 */
enum class MediaType(private val type: Type, private val subType: String) {

    HTML(Type.TEXT, "html");

    override fun toString(): String {
        return if (type.equals(Type.TEXT)) {
            "$type/$subType;charset=$CHARACTER_SET"
        } else {
            "$type/$subType"
        }
    }
}

enum class Type(val token: String) {

    TEXT("text")
}
