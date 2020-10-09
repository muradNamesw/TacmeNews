package com.tacme.news.helpers

//
//  NetworkHandler.java
//
//  Created by Ahmad on 2/14/18.
//  Copyright © 2018. All rights reserved.


import android.content.ContentValues.TAG
import android.os.StrictMode
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.ANRequest
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.DownloadListener
import com.androidnetworking.interfaces.OkHttpResponseListener
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import com.tacme.news.utils.StringUtils
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap


typealias ResponseOnComplete = (status: StatusCode, response: JSONObject?) -> Unit
typealias ResponseOnError = (code: StatusCode, response: JSONObject?, body: String?, details: String) -> Unit
typealias ResponseOnDownloadComplete = (dir: String, file: String) -> Unit
typealias ResponseOnDownloadError = (error: DownloadError) -> Unit
typealias ResponseOnProgress = (bytesUploaded: Long, totalBytes: Long) -> Unit


object NetworkHandler {

    val kTimeOut: Long = 120

    var incompletedRequests = HashMap<String, Any>()

    fun sendRequest(
        method: HttpMethod,
        urlString: String,
        parameters: HashMap<String, Any>,
        headers: HashMap<String, String>,
        onComplete: ResponseOnComplete,
        onError: ResponseOnError
    ) {

        try {

            var requestBuilder: ANRequest.GetRequestBuilder<*>

            when (method) {

                HttpMethod.head -> requestBuilder = AndroidNetworking.head(urlString)

                HttpMethod.options -> requestBuilder = AndroidNetworking.options(urlString)

                HttpMethod.get -> requestBuilder = AndroidNetworking.get(urlString)

                else -> requestBuilder = AndroidNetworking.get(urlString)
            }

            for (key in headers.keys) {

                val value = headers[key]

                requestBuilder = requestBuilder.addHeaders(key, value)

            }

            for (key in parameters.keys) {

                val value = parameters[key].toString() + ""

                requestBuilder = requestBuilder.addQueryParameter(key, value) // posting java object

            }


            val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(kTimeOut, TimeUnit.SECONDS)
                .readTimeout(kTimeOut, TimeUnit.SECONDS)
                .writeTimeout(kTimeOut, TimeUnit.SECONDS)
                .build()

            val request = requestBuilder.setPriority(Priority.HIGH)
                .setOkHttpClient(okHttpClient)
                .build()


            val policy = StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()

            StrictMode.setThreadPolicy(policy)


            request.getAsOkHttpResponse(object : OkHttpResponseListener {

                override fun onResponse(response: Response) {

                    Log.d(TAG, response.toString())

                    handleOnResponse(response, onComplete, onError)

                }

                override fun onError(error: ANError) {

                    // handle error
                    Log.d("Error", error.toString())

                    handleOnError(error, onError)

                    incompletedRequests = HashMap()
                    incompletedRequests["url"] = urlString
                    incompletedRequests["parameters"] = parameters
                    incompletedRequests["headers"] = headers
                    incompletedRequests["method"] = method
                    incompletedRequests["onComplete"] = onComplete
                    incompletedRequests["onError"] = onError
                    incompletedRequests["type"] = RequestType.getRequest

                }


            })


        } catch (e: Exception) {
            e.printStackTrace()
            onError(StatusCode.unknown, null, e.message, e.localizedMessage)
        }

    }

    fun sendRawDataRequest(
        method: HttpMethod,
        urlString: String,
        parameters: HashMap<String, Any>,
        headers: HashMap<String, String>,
        onComplete: ResponseOnComplete,
        onError: ResponseOnError
    ) {

        try {

            var requestBuilder: ANRequest.PostRequestBuilder<*>

            when (method) {

                HttpMethod.put -> requestBuilder = AndroidNetworking.put(urlString)

                HttpMethod.patch -> requestBuilder = AndroidNetworking.patch(urlString)

                HttpMethod.delete -> requestBuilder = AndroidNetworking.delete(urlString)

                HttpMethod.post -> requestBuilder = AndroidNetworking.post(urlString)

                else -> requestBuilder = AndroidNetworking.post(urlString)
            }

            for (key in headers.keys) {

                val value = headers[key]

                requestBuilder = requestBuilder.addHeaders(key, value)

            }


            val jsonObject = JSONObject()

            for (key in parameters.keys) {

                val value = parameters[key]

                if (value is HashMap<*, *>) {

                    val subJsonObject = JSONObject()

                    for (subkey in value.keys) {
                        subJsonObject.put(subkey as String, value[subkey])
                    }

                    jsonObject.put(key, subJsonObject)

                } else {
                    jsonObject.put(key, value)
                }

                //requestBuilder = requestBuilder.addBodyParameter(key, value); // posting java object

            }

            requestBuilder = requestBuilder.addJSONObjectBody(jsonObject) // posting json

            val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(kTimeOut, TimeUnit.SECONDS)
                .readTimeout(kTimeOut, TimeUnit.SECONDS)
                .writeTimeout(kTimeOut, TimeUnit.SECONDS)
                .build()

            val request = requestBuilder.setPriority(Priority.HIGH)
                .setOkHttpClient(okHttpClient)
                .build()

            val policy = StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
            StrictMode.setThreadPolicy(policy)


            request.getAsOkHttpResponse(object : OkHttpResponseListener {

                override fun onResponse(response: Response) {

                    Log.d(TAG, response.toString())

                    //Thread {
                    handleOnResponse(response, onComplete, onError)
                    //}.start()

                }

                override fun onError(error: ANError) {
                    // handle error
                    Log.d("Error", error.toString())

                    handleOnError(error, onError)

                    incompletedRequests = HashMap()
                    incompletedRequests["url"] = urlString
                    incompletedRequests["parameters"] = parameters
                    incompletedRequests["headers"] = headers
                    incompletedRequests["method"] = method
                    incompletedRequests["onComplete"] = onComplete
                    incompletedRequests["onError"] = onError
                    incompletedRequests["type"] = RequestType.postRequest
                }


            })

        } catch (e: Exception) {
            e.printStackTrace()
            onError(StatusCode.unknown, null, e.message, e.localizedMessage)
        }

    }

    fun sendMultiPartRequest(
        urlString: String,
        parameters: HashMap<String, Any>,
        files: HashMap<String, File>,
        headers: HashMap<String, String>,
        onComplete: ResponseOnComplete,
        onError: ResponseOnError,
        onProgress: ResponseOnProgress
    ) {

        try {

            var requestBuilder: ANRequest.MultiPartBuilder<*> = AndroidNetworking.upload(urlString)

            for (key in headers.keys) {

                val value = headers[key]

                requestBuilder = requestBuilder.addHeaders(key, value)

            }

            for (key in parameters.keys) {

                val value = parameters[key].toString() + ""

                requestBuilder =
                    requestBuilder.addMultipartParameter(key, value) // posting java object

            }

            for (key in files.keys) {

                val file = files[key]
                val extension = file?.name?.substring(file.name.lastIndexOf("."))
                var newFileName= file

                if(StringUtils.isProbablyArabic(file?.name?.replace(extension?:"",""))){
                    val stamp = Date().toString("yyyy-MM-dd hh-mm-ss")
                    val suggestedName = String.format("t_%s.pdf", stamp)

                    val filePath=file?.absolutePath?.replace(file.name,suggestedName)
                     newFileName= File(filePath)
                    file?.renameTo(newFileName)
                }
                requestBuilder = requestBuilder.addMultipartFile(key, newFileName)

            }


            val request = requestBuilder.setPriority(Priority.HIGH)
                .build()

            request.setUploadProgressListener { bytesUploaded, totalBytes ->
                // do anything with progress
                onProgress(bytesUploaded, totalBytes)
            }
                .getAsOkHttpResponse(object : OkHttpResponseListener {

                    override fun onResponse(response: Response) {

                        Log.d(TAG, response.toString())

                        handleOnResponse(response, onComplete, onError)

                    }

                    override fun onError(error: ANError) {
                        // handle error
                        Log.d("Error", error.toString())

                        handleOnError(error, onError)

                        incompletedRequests = HashMap()
                        incompletedRequests["url"] = urlString
                        incompletedRequests["parameters"] = parameters
                        incompletedRequests["files"] = files

                        incompletedRequests["headers"] = headers
                        incompletedRequests["onComplete"] = onComplete
                        incompletedRequests["onError"] = onError
                        incompletedRequests["onProgress"] = onProgress

                        incompletedRequests["type"] = RequestType.multiPartRequest

                    }


                })

        } catch (e: Exception) {
            e.printStackTrace()
            onError(StatusCode.unknown, null, e.message, e.localizedMessage)
        }

    }

    fun sendDownloadRequest(
        urlString: String,
        parameters: HashMap<String, Any>,
        headers: HashMap<String, String>,
        dirPath: String,
        fileName: String,
        onComplete: ResponseOnComplete,
        onError: ResponseOnError,
        onProgress: ResponseOnProgress,
        onDownload: ResponseOnDownloadComplete
    ) {

        try {

            var requestBuilder: ANRequest.DownloadBuilder<*> =
                AndroidNetworking.download(urlString, dirPath, fileName)

            for (key in headers.keys) {

                val value = headers[key]

                requestBuilder = requestBuilder.addHeaders(key, value)

            }

            for (key in parameters.keys) {

                val value = parameters[key].toString() + ""

                requestBuilder = requestBuilder.addPathParameter(key, value) // posting java object

            }

            val request = requestBuilder.setPriority(Priority.HIGH)
                .build()

            request.setDownloadProgressListener { bytesUploaded, totalBytes ->
                // do anything with progress
                onProgress(bytesUploaded, totalBytes)
            }
                .startDownload(object : DownloadListener {

                    override fun onDownloadComplete() {
                        // do anything after completion

                        //request.get.getStatusLine().getStatusCode();

                        onComplete(StatusCode.ok, JSONObject())
                    }

                    override fun onError(error: ANError) {
                        // handle error
                        Log.d("Error", error.toString())

                        handleOnError(error, onError)

                        incompletedRequests = HashMap()
                        incompletedRequests["url"] = urlString
                        incompletedRequests["parameters"] = parameters
                        incompletedRequests["dirPath"] = dirPath
                        incompletedRequests["fileName"] = fileName
                        incompletedRequests["headers"] = headers
                        incompletedRequests["onComplete"] = onComplete
                        incompletedRequests["onError"] = onError
                        incompletedRequests["onProgress"] = onProgress
                        incompletedRequests["onDownload"] = onDownload
                        incompletedRequests["type"] = RequestType.downloadRequest
                    }
                })

        } catch (e: Exception) {
            e.printStackTrace()
            onError(StatusCode.unknown, null, e.message, e.localizedMessage)
        }

    }

    fun sendDownloadPostRequest(
        urlString: String,
        parameters: HashMap<String, Any>,
        headers: HashMap<String, String>,
        dirPath: String,
        fileName: String,
        onComplete: ResponseOnComplete,
        onError: ResponseOnError,
        onDownloadError: ResponseOnDownloadError
    ) {

        try {

            val client = OkHttpClient().newBuilder()
                .connectTimeout(kTimeOut, TimeUnit.SECONDS)
                .readTimeout(kTimeOut, TimeUnit.SECONDS)
                .writeTimeout(kTimeOut, TimeUnit.SECONDS)
                .build()

            val mediaType: MediaType? = MediaType.parse("application/json")

            val jsonObject = JSONObject()

            for (key in parameters.keys) {

                val value = parameters[key].toString() + ""

                jsonObject.put(key, value)

            }

            val body = RequestBody.create(mediaType, jsonObject.toString())


            var requestBuilder: Request.Builder? = Request.Builder()
                .url(urlString)
                .post(body)

            for (key in headers.keys) {

                val value = headers[key]

                requestBuilder = requestBuilder?.addHeader(key, value)

            }

            val request: Request? = requestBuilder?.build()

            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    onError(StatusCode.unknown, null, e.message, e.localizedMessage)
                }

                override fun onResponse(call: Call, response: Response) {

                    try {
                        val statusCode = StatusCode.getByValue(response.code())

                        println(response.code())

                        if (statusCode == StatusCode.ok) {
                            val inputStream: InputStream? = response.body()?.byteStream()

                            if (inputStream != null) {


                                val dir = File(dirPath)

                                if (!dir.exists()) {
                                    dir.mkdirs()
                                }

                                val file = File(dirPath, fileName)

                                if (file.exists()) file.delete()

                                file.createNewFile()

                                file.outputStream().use { fileOut ->
                                    inputStream.copyTo(fileOut)
                                }

                                onComplete(StatusCode.ok, JSONObject())
                            }
                        }else{
                            onComplete(statusCode, JSONObject(response.body()?.string()?:""))
                        }

                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                        onDownloadError(DownloadError.fileNotFound)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        onDownloadError(DownloadError.canNotReadFile)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        onDownloadError(DownloadError.canNotSaveToExternalStorage)
                    }

                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
            onError(StatusCode.unknown, null, e.message, e.localizedMessage)
        }

    }

    fun retryRequest() {

        if (incompletedRequests.containsKey("type")) {

            val type = incompletedRequests["type"] as RequestType

            when (type) {

                RequestType.getRequest -> {
                    sendRequest(
                        incompletedRequests["method"] as HttpMethod,
                        incompletedRequests["url"] as String,
                        incompletedRequests["parameters"] as HashMap<String, Any>,
                        incompletedRequests["headers"] as HashMap<String, String>,
                        incompletedRequests["onComplete"] as ResponseOnComplete,
                        incompletedRequests["onError"] as ResponseOnError
                    )
                }

                RequestType.postRequest -> {
                    sendRawDataRequest(
                        incompletedRequests["method"] as HttpMethod,
                        incompletedRequests["url"] as String,
                        incompletedRequests["parameters"] as HashMap<String, Any>,
                        incompletedRequests["headers"] as HashMap<String, String>,
                        incompletedRequests["onComplete"] as ResponseOnComplete,
                        incompletedRequests["onError"] as ResponseOnError
                    )
                }

                RequestType.multiPartRequest -> {
                    sendMultiPartRequest(
                        incompletedRequests["url"] as String,
                        incompletedRequests["parameters"] as HashMap<String, Any>,
                        incompletedRequests["files"] as HashMap<String, File>,
                        incompletedRequests["headers"] as HashMap<String, String>,
                        incompletedRequests["onComplete"] as ResponseOnComplete,
                        incompletedRequests["onError"] as ResponseOnError,
                        incompletedRequests["onProgress"] as ResponseOnProgress
                    )
                }

                RequestType.downloadRequest -> {
                    sendDownloadRequest(
                        incompletedRequests["url"] as String,
                        incompletedRequests["parameters"] as HashMap<String, Any>,
                        incompletedRequests["headers"] as HashMap<String, String>,
                        incompletedRequests["dirPath"] as String,
                        incompletedRequests["fileName"] as String,
                        incompletedRequests["onComplete"] as ResponseOnComplete,
                        incompletedRequests["onError"] as ResponseOnError,
                        incompletedRequests["onProgress"] as ResponseOnProgress,
                        incompletedRequests["onDownload"] as ResponseOnDownloadComplete
                    )
                }
            }
        }
    }

    private fun handleOnResponse(
        response: Response,
        onComplete: ResponseOnComplete,
        onError: ResponseOnError
    ) {

        val statusCode = StatusCode.getByValue(response.code())

        var responseString = ""

        try {
            responseString = response.body()?.string() ?: ""
        } catch (e: Exception) {//IOException //IllegalStateException
            e.printStackTrace()
            onError(statusCode, null, responseString, e.message ?: "")
            return
        }

        var json: Any? = null

        try {

            var jsonObject = JSONObject()

            if (responseString.isEmpty()) {
                //TODO this is invalid case for REST API's (successful response with empty body 😠😠)
                // It shouldn't, just to handle API issues :) ... 😂😂
                onComplete(statusCode, jsonObject)
                return
            }

            json = JSONTokener(responseString).nextValue()

            if (json == null) {
                //TODO this is invalid case for REST API's (successful response with empty body 😠😠)
                // It shouldn't, just to handle API issues :) ... 😂😂
                onComplete(statusCode, jsonObject)
                //listener.onError( statusCode, null, "Malformed output", "Malformed output");
                return
            }

            if (json is JSONObject) {
                //you have an object

                jsonObject = JSONObject(responseString)

                onComplete(statusCode, jsonObject)

            } else if (json is JSONArray) {
                //you have an array

                val jsonArray = JSONArray(responseString)

                jsonObject.put("output", jsonArray)

                onComplete(statusCode, jsonObject)

            } else {
                //TODO this is invalid case for REST API's (successful response with empty body 😠😠)
                // It shouldn't, just to handle API issues :) ... 😂😂
                onComplete(statusCode, jsonObject)
                //onError( statusCode, null, "Malformed output", "Malformed output");
            }

        } catch (e: JSONException) {
            e.printStackTrace()
            onError(statusCode, null, responseString, e.message ?: "")
        }

    }

    private fun handleOnError(error: ANError, onError: ResponseOnError) {

        if (error.errorCode != 0) {

            // received ANError from server

            // error.getErrorCode() - the ANError code from server
            // error.getErrorBody() - the ANError body from server
            // error.getErrorDetail() - just a ANError detail

            Log.d(TAG, "onError errorCode : " + error.errorCode)
            Log.d(TAG, "onError errorBody : " + error.errorBody)
            Log.d(TAG, "onError errorDetail : " + error.errorDetail)

        } else {
            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
            Log.d(TAG, "onError errorDetail : " + error.errorDetail)
        }

        val statusCode = StatusCode.getByValue(error.errorCode)

        var jsonObject = JSONObject()

        //getting the whole json object from the response
        try {
            jsonObject = JSONObject(error.errorBody.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
            onError(statusCode, null, error.errorBody, error.errorDetail)
            return
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            onError(statusCode, null, error.errorBody, error.errorDetail)
            return
        }

        onError(statusCode, jsonObject, error.errorBody, error.errorDetail)

        //incompletedRequests = new HashMap<String, Object>();
        //
        //incompletedRequests.put("url",               urlString);
        //incompletedRequests.put("parameters",        parameters);
        //incompletedRequests.put("headers",           headers);
        //incompletedRequests.put("images",            images);
        //incompletedRequests.put("completionHandler", complete);
        //incompletedRequests.put("errorHandler",      failureHandler);
        //incompletedRequests.put("progressHandler",   progressCompletion);
        //incompletedRequests.put("type",              3);
    }

}

enum class HttpMethod private constructor(var rawVal: String) {

    options("OPTIONS"),
    get("GET"),
    head("HEAD"),
    post("POST"),
    put("PUT"),
    patch("PATCH"),
    delete("DELETE"),
    trace("TRACE"),
    connect("CONNECT")

}

enum class StatusCode private constructor(var value: Int) {

    unknown(0),

    // 1×× Informational
    //continue                      = 100 ),
    //switchingProtocols            = 101 ),
    //processing                    = 102 ),


    //2×× Successfull
    ok(200),
    created(201),
    accepted(202),
    nonAuthoritativeInformation(203),
    noContent(204),
    resetContent(205),
    partialContent(206),
    multiStatus(207),
    alreadyReported(208),
    imUsed(226),

    //3×× Redirection
    multipleChoices(300),
    movedPermanently(301),
    found(302),
    seeOther(303),
    notModified(304),
    useProxy(305),
    unused(306),
    temporaryRedirect(307),
    permanentRedirect(308),

    //4×× Client Error 4xx
    badRequest(400),
    unauthorized(401),
    paymentRequired(402),
    forbidden(403),
    notFound(404),
    methodNotAllowed(405),
    notAcceptable(406),
    proxyAuthenticationRequired(407),
    requestTimeout(408),
    conflict(409),
    gone(410),
    lengthRequired(411),
    preconditionFailed(412),
    requestEntityTooLarge(413),
    requestUriTooLong(414),
    unsupportedMediaType(415),
    requestedRangeNotSatisfiable(416),
    expectationFailed(417),

    imATeapot(418),
    misdirectedRequest(421),
    unprocessableEntity(422),
    locked(423),
    failedDependency(424),
    upgradeRequired(426),
    preconditionRequired(428),
    tooManyRequests(429),
    requestHeaderFieldsTooLarge(431),
    connectionClosedWithoutResponse(444),
    unavailableForLegalReasons(451),
    clientClosedRequest(499),

    //5×× Server Error 5xx
    internalServerError(500),
    notImplemented(501),
    badGateway(502),
    serviceUnavailable(503),
    gatewayTimeout(504),
    httpVersionNotSupported(505),
    variantAlsoNegotiates(506),
    insufficientStorage(507),
    loopDetected(508),
    notExtended(510),
    networkAuthenticationRequired(511),
    networkConnectTimeoutError(599);


    companion object {

        fun getByValue(value: Int): StatusCode {
            for (statusCode in StatusCode.values()) {
                if (statusCode.value == value)
                    return statusCode
            }

            return unknown
        }
    }
}

enum class RequestType private constructor(var value: Int) {

    getRequest(1),
    postRequest(2),
    multiPartRequest(3),
    downloadRequest(4);

    companion object {

        fun getBy(value: Int): RequestType {

            for (type in RequestType.values()) {
                if (type.value == value)
                    return type
            }

            return getRequest
        }
    }
}

enum class DownloadError private constructor(var value: Int) {

    fileNotFound(0),
    canNotReadFile(1),
    canNotSaveToExternalStorage(2)

}
