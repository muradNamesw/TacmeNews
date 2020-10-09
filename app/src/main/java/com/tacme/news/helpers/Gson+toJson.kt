package com.tacme.news.helpers

import com.google.gson.Gson

fun Gson.toJson(src: Any) : String = toJson(src as java.lang.Object)