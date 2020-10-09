package com.tacme.news.helpers

object SharedMemory {

    private var data: MutableMap<String, Any>? = null

    init {
        data = mutableMapOf<String, Any>()
    }

    operator fun set(key: String, value: Any) {
        //data!![key] = value
        data?.put(key, value)
    }

    operator fun get(key: String): Any? {
        //return data!![key]
        return data?.get(key)
    }

    fun removeValue(key: String) {

        //val value = data!![key]
        val value = data?.get(key)

        data?.remove(value )
    }

    fun resetData() {
        data = mutableMapOf<String, Any>()
    }

}