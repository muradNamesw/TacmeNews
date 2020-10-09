package com.tacme.news.helpers

fun String?.handleNull(): String {

    return if ( this == ("null") || this == null ) "" else this

}
