package com.tacme.news.helpers

import java.text.SimpleDateFormat
import java.util.*

fun Date?.toString(format: String): String {

    this?.let{

        //yyyy-mm-dd hh:mm:ss
        val dateFormat = SimpleDateFormat(format,Locale.ENGLISH)

        //to convert Date to String, use format method of SimpleDateFormat class.

        return dateFormat.format(it)

    }

    return ""
}