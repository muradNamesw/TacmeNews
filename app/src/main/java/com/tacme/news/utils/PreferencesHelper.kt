package com.tacme.news.utils

import android.content.Context
import android.content.SharedPreferences

import org.json.JSONException
import org.json.JSONObject

import com.tacme.news.app.MyApplication
import com.tacme.news.networking.GlobalKeys

import com.tacme.news.networking.GlobalKeys.kPreferencesKey

class PreferencesHelper {

    private var settings: SharedPreferences? = null

    operator fun set(key: String, value: Array<String>) {

        val editor = settings?.edit()

        editor?.putStringSet(key, value.toMutableSet())

        editor?.apply()

    }

    operator fun get(key: String,value: Array<String>): Array<String>? {

        return settings?.getStringSet(key, value.toMutableSet())?.toTypedArray()?: arrayOf()
    }

    operator fun set(key: String, value: String) {

        val editor = settings?.edit()

        editor?.putString(key, value)

        editor?.apply()

    }

    operator fun get(key: String, defaultVal: String): String? {

        return settings?.getString(key, defaultVal) ?: ""
    }

    //More wonder 😍😍 from kotlin
    operator fun get(key: String, defaultVal: JSONObject): JSONObject? {

        val value = settings?.getString(key, "") ?: ""

        var obj: JSONObject? = null

        try {
            obj = JSONObject(value)
        } catch (e: JSONException) {
            obj = defaultVal
        }

        return obj
    }

    operator fun get(key: String, defaultVal: Int): Int {

        //int value = settings.getInt(key, defaultVal);

        val value = settings?.getString(key, defaultVal.toString() + "") ?: "0"

        return Integer.parseInt(value)
    }

    operator fun get(key: String, defaultVal: Float): Float {

        //float value = settings.getFloat(key, defaultVal);

        val value = settings?.getString(key, defaultVal.toString() + "") ?: "0.0"

        return java.lang.Float.parseFloat(value)
    }

    operator fun get(key: String, defaultVal: Boolean): Boolean {

        //boolean value = settings.getBoolean(key, defaultVal);

        val value = settings?.getString(key, defaultVal.toString() + "") ?: "false"

        return java.lang.Boolean.parseBoolean(value)
    }

    fun removeValue(key: String) {

        val editor = settings?.edit()

        editor?.remove(key)

        editor?.apply()

    }



    fun getResourceString(resId: Int): String {
        return context?.resources?.getString(resId) ?: ""
    }

    companion object {

        private var instance: PreferencesHelper? = null

        private var context: Context? = null

        fun initInstance(pContext: Context) {

            context = pContext

        }

        fun getInstance(): PreferencesHelper {

            if (instance == null) {

                instance = PreferencesHelper()

                if (context == null) {
                    context = MyApplication.instance?.applicationContext
                }

                context?.let {

                    instance?.settings = it.getSharedPreferences(kPreferencesKey, 0)

                }

            }

            return instance!!
        }
    }
}