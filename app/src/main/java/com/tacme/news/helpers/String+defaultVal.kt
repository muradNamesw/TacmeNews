package com.tacme.news.helpers

fun String.defaultVal(value: String): String {

    if( this.equals("") ) {
        return value
    }

    return this
}