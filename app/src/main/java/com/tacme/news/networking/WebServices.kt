package com.tacme.news.networking


import android.util.Log
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import org.json.JSONObject
import com.tacme.news.helpers.*
import com.tacme.news.helpers.HttpMethod.*
import com.tacme.news.model.mvp.NewsMVPListDec
import com.tacme.news.model.mvvm.*

import com.tacme.news.networking.GlobalKeys.kAppLanguage
import com.tacme.news.networking.GlobalKeys.kAuthToken
import com.tacme.news.networking.GlobalKeys.kDefaultLang
import com.tacme.news.networking.WebServiceError.*
import com.tacme.news.utils.PreferencesHelper
import java.lang.reflect.Field
import java.util.*



typealias FailureResult = (WebServiceError) -> Unit

typealias SuccessfullResponseResult = (response: JSONObject) -> Unit



typealias NewsMVVMtListResult = (status: Int, newsList: Array<NewsListDec>, totalItems: Int) -> Unit
typealias NewsMVPtListResult = (status: Int, newsList: Array<NewsMVPListDec>, totalItems: Int) -> Unit

class SuperclassExclusionStrategy : ExclusionStrategy {
    override fun shouldSkipClass(arg0: Class<*>?): Boolean {
        return false
    }

    override fun shouldSkipField(fieldAttributes: FieldAttributes): Boolean {
        val fieldName = fieldAttributes.name
        val theClass = fieldAttributes.declaringClass
        return isFieldInSuperclass(theClass, fieldName)
    }

    private fun isFieldInSuperclass(
        subclass: Class<*>,
        fieldName: String
    ): Boolean {
        var superclass = subclass.superclass
        var field: Field?
        while (superclass != null) {
            field = getField(superclass, fieldName)
            if (field != null) return true
            superclass = superclass.superclass
        }
        return false
    }

    private fun getField(theClass: Class<*>, fieldName: String): Field? {
        return try {
            theClass.getDeclaredField(fieldName)
        } catch (e: java.lang.Exception) {
            null
        }
    }
}


enum class WebServiceError {

    noInternetConnection,
    malformedOutput,
    invalidInputs, //badRequest
    invalidRequest, //invalid url or invalid method
    requestTimeout,
    authenticationFailed,
    tooManyRequests,
    serverProblem,
    noResults,
    unknownError,
    requestNotSent,
    invalidFileInputOutput

    //......,

}

object WebServices {

    val TAG = "WebServices"




    // MARK: common
    private fun handleSuccess(
        status: StatusCode,
        response: JSONObject?,
        onSuccess: SuccessfullResponseResult,
        onFailure: FailureResult
    ) {

        try {

            Log.d(TAG, response.toString())

            when {

                status.value in 1..399 && response != null -> {

                    onSuccess(response)

                }

                status.value in 401..403 -> {
                    onFailure(authenticationFailed)
                }

                status.value == 400 || status.value in 404..499 -> {
                    onFailure(invalidRequest)
                }

                else -> {
                    onFailure(serverProblem)
                }

            }

        } catch (e: Exception) {
            e.printStackTrace()
            onFailure(serverProblem)
        }

    }


    private fun handleFailure(
        status: StatusCode,
        response: JSONObject?,
        body: String?,
        details: String,
        onFailure: FailureResult
    ) {

        Log.d(TAG, "${status.value}")
        //Log.d(TAG, body)
        Log.d(TAG, details)

        if (details == "connectionError") {
            onFailure(noInternetConnection)
        } else {

            when (status.value) {

                in 401..403 -> {
                    onFailure(authenticationFailed)
                }
                else -> {
                    onFailure(serverProblem)
                }

            }
        }
    }

    private fun getHeaders(): HashMap<String, String> {

        val preferences = PreferencesHelper.getInstance()

        val lang = preferences[kAppLanguage, kDefaultLang]
        val token = preferences[kAuthToken, ""]

        val headers = HashMap<String, String>()

        headers["Content-Type"] = "application/json"
        headers["Accept"] = "application/json"
        headers["Accept-Language"] = lang ?: ""
        headers["token"] = token ?: ""
        headers["cache-control"] = "no-cache"
        //headers["api-key"]         = kBackendApiKey

        return headers
    }





    fun newsListMVVM(
        country: String,
        category: String,
        apiKey: String,
        result: NewsMVVMtListResult,
        onFailure: FailureResult
    ) {

        try {

            val headers = getHeaders()

            val parameters = HashMap<String, Any>()



            val urlString = BackendRoute.BASE_URL + BackendRoute.NEWS_MAIN + "country="+country+"&apiKey="+apiKey+"&category="+category+""

            Log.d(TAG, "URL:        $urlString")
            Log.d(TAG, "Headers:    $headers")
            Log.d(TAG, "Parameters: $parameters")

            NetworkHandler.sendRequest(get, urlString, parameters, headers, { status, response ->

                handleSuccess(status, response, { response ->

                    val totalResults = response.getInt("totalResults")

                    val array = response.getJSONArray("articles")

                    val newsListDec = NewsListDec.fromJson(array, true).toTypedArray()


                    result(1, newsListDec, totalResults)

                }, onFailure)

            }, { status, jsonObject, body, details ->

                handleFailure(status, jsonObject, body, details, onFailure)

            })

        } catch (e: Exception) {
            e.printStackTrace()
            onFailure(requestNotSent)
        }

    }



    fun newsListMVP(
        country: String,
        category: String,
        apiKey: String,
        result: NewsMVPtListResult,
        onFailure: FailureResult
    ) {

        try {

            val headers = getHeaders()

            val parameters = HashMap<String, Any>()



            val urlString = BackendRoute.BASE_URL + BackendRoute.NEWS_MAIN + "country="+country+"&apiKey="+apiKey+"&category="+category+""

            Log.d(TAG, "URL:        $urlString")
            Log.d(TAG, "Headers:    $headers")
            Log.d(TAG, "Parameters: $parameters")

            NetworkHandler.sendRequest(get, urlString, parameters, headers, { status, response ->

                handleSuccess(status, response, { response ->

                    val totalResults = response.getInt("totalResults")

                    val array = response.getJSONArray("articles")

                    val newsListDec = NewsMVPListDec.fromJson(array, true).toTypedArray()


                    result(1, newsListDec, totalResults)

                }, onFailure)

            }, { status, jsonObject, body, details ->

                handleFailure(status, jsonObject, body, details, onFailure)

            })

        } catch (e: Exception) {
            e.printStackTrace()
            onFailure(requestNotSent)
        }

    }


}