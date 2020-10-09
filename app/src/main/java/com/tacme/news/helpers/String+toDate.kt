package com.tacme.news.helpers

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String?.toDate(format: String): Date? {

    var date: Date? = null

    val dateFormat = SimpleDateFormat(format, Locale.ENGLISH)

    try {

        date = dateFormat.parse(this)

    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return date
}