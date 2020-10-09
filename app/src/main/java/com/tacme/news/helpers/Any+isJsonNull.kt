package com.tacme.news.helpers

fun Any.isJsonNull(): Boolean {

    if ( this == null || this.toString() == "null" ) {
        return true
    }

    return false
}