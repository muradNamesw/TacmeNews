package com.tacme.news.helpers

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.Iterator
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.set


object Utils {

    fun stringToDate(dateString: String, format: String): Date? {

        var date: Date? = null

        val dateFormat = SimpleDateFormat(format, Locale.ENGLISH)

        try {

            date = dateFormat.parse(dateString)

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    fun dateToString(date: Date?, format: String): String {

        date.let{

            //yyyy-mm-dd hh:mm:ss
            val dateFormat = SimpleDateFormat(format)

            //to convert Date to String, use format method of SimpleDateFormat class.

            return dateFormat.format(date)
        }

        return ""

    }

    fun getInt(`val`: String): Int {

        try {

            return Integer.parseInt(`val`)

        } catch (ex: Exception) {
            return 0
        }

    }


    @Throws(JSONException::class)
    fun jsonToMap(json: JSONObject): Map<String, Any>? {

        var retMap: Map<String, Any> = HashMap()

        if (json !== JSONObject.NULL) {
            retMap = toMap(json)
        }

        return retMap
    }

    @Throws(JSONException::class)
    fun toMap(jsonObject: JSONObject): Map<String, Any> {

        val map: MutableMap<String, Any> = HashMap()

        val keysItr: Iterator<String> = jsonObject.keys()

        while (keysItr.hasNext()) {

            val key = keysItr.next()

            var value: Any = jsonObject.get(key)

            if (value is JSONArray) {
                value = toList(value as JSONArray)
            } else if (value is JSONObject) {
                value = toMap(value as JSONObject)
            }

            map[key] = value
        }
        return map
    }

    @Throws(JSONException::class)
    fun toList(jsonArray: JSONArray): List<Any> {

        val list: MutableList<Any> = ArrayList()

        for (i in 0 until jsonArray.length()) {

            var value: Any = jsonArray.get(i)

            if (value is JSONArray) {
                value = toList(value as JSONArray)
            } else if (value is JSONObject) {
                value = toMap(value as JSONObject)
            }

            list.add(value)
        }

        return list
    }
}

