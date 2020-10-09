package com.tacme.news.utils

class ParseException : Exception {

    constructor() : super() {}

    constructor(message: String) : super(message) {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}

    constructor(cause: Throwable) : super(cause) {}

    //protected PreferencesException(String message, Throwable cause,
    //                           boolean enableSuppression,
    //                           boolean writableStackTrace) {
    //    super(message, cause, enableSuppression, writableStackTrace);
    //}
}
