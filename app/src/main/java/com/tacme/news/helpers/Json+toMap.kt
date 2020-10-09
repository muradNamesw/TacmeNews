package com.tacme.news.helpers

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/*fun Date?.toString(format: String): String {

    this?.let{

        //yyyy-mm-dd hh:mm:ss
        val dateFormat = SimpleDateFormat(format,Locale.ENGLISH)

        //to convert Date to String, use format method of SimpleDateFormat class.

        return dateFormat.format(it)

    }

    return ""
}*/

@Throws(JSONException::class)
fun JSONObject?.toMapDictionary(): Map<String, Any>? {

    var retMap: Map<String, Any> = HashMap()

    this?.let {

        if (it !== JSONObject.NULL) {
            retMap = it.toMap()
        }

    }
    return retMap
}

@Throws(JSONException::class)
fun JSONObject?.toMap(): Map<String, Any> {

    val map: MutableMap<String, Any> = HashMap()

    this?.let {

        val keysItr: Iterator<String> = it.keys()

        while (keysItr.hasNext()) {

            val key = keysItr.next()

            var value: Any = this.get(key)

            if (value is Long || value is Int || value is Float || value is Double){
                value = value.toString()
            }

            if (value is JSONArray) {
                value = (value as JSONArray).toList()
            } else if (value is JSONObject) {
                value = (value as JSONObject).toMap()
            }

            map[key] = value
        }
    }

    return map
}

@Throws(JSONException::class)
fun JSONArray?.toList(): Array<Any> {

    val list: MutableList<Any> = ArrayList()

    this?.let {

        for (i in 0 until it.length()) {

            var value: Any = it.get(i)

            if (value is JSONArray) {
                value = (value as JSONArray).toList()
            } else if (value is JSONObject) {
                value = (value as JSONObject).toMap()
            }

            list.add(value)
        }
    }

    return list.toTypedArray()
}