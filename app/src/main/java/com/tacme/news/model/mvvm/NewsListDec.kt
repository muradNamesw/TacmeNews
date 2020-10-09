package com.tacme.news.model.mvvm


import com.apprikot.listable.interfaces.Listable
import com.apprikot.listable.model.HolderClass
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import com.tacme.news.R
import com.tacme.news.helpers.toMapDictionary
import com.tacme.news.utils.ParseException
import com.tacme.news.view.viewholders.ListNewsDecVH
import java.util.*

class NewsListDec : Listable {


    @SerializedName(value = "author")
    @Expose(deserialize = false)
    var author: String? = null

    @SerializedName(value = "title")
    @Expose(deserialize = false)
    var title: String? = null


    @SerializedName(value = "description")
    @Expose(deserialize = false)
    var description: String? = null


    @SerializedName(value = "url")
    @Expose(deserialize = false)
    var url: String? = null



    @SerializedName(value = "urlToImage")
    @Expose(deserialize = false)
    var urlToImage: String? = null

    @SerializedName(value = "publishedAt")
    @Expose(deserialize = false)
    var publishedAt: String? = null










    @Throws(ParseException::class)
    @JvmOverloads
    constructor(jsonObject: JSONObject, isList: Boolean = true) {

        if (isList) {
            this.parseAsList(jsonObject)
        }

    }

    @Throws(ParseException::class)
    fun parseAsList(jsonObject: JSONObject) {

        try {

            val dictionary = jsonObject.toMapDictionary()


            this.author = dictionary?.get("author") as? String ?: ""
            this.title = dictionary?.get("title") as? String ?: ""
            this.description = dictionary?.get("description") as? String ?: ""
            this.urlToImage = dictionary?.get("urlToImage") as? String ?: ""
            this.publishedAt = dictionary?.get("publishedAt") as? String ?: ""
            this.url = dictionary?.get("url") as? String ?: ""



        } catch (ex: JSONException) {
            ex.printStackTrace()
            throw ParseException("Error in parsing object")
        }

    }



    companion object {

        @Throws(ParseException::class)
        fun fromJson(jsonArray: JSONArray, isList: Boolean?): ArrayList<NewsListDec> {

            val list = ArrayList<NewsListDec>()

            for (i in 0 until jsonArray.length()) {

                try {

                    list.add(NewsListDec(jsonArray.getJSONObject(i), (isList ?: false)))

                } catch (e: Exception) {
                    throw ParseException("Error in parsing list")
                }

            }

            return list
        }
    }

    override fun getListItemType(): HolderClass {
        return HolderClass(ListNewsDecVH::class.java, R.layout.list_news_item_mvvm)
    }
}

