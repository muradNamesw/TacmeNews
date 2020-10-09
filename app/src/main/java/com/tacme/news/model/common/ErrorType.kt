package com.tacme.news.model.enums

import com.tacme.news.R

/**
 * Created by Murad Adnan on 3/26/19.
 */
enum class ErrorType(var image: Int, var message: Int) {

    NO_INTERNET(R.drawable.img_no_internet, R.string.common_error__no_internet),
    SERVER_ERROR(R.drawable.ic_maintenance, R.string.common_error__server_error),
    UNKNOWN_ERROR(R.drawable.ic_maintenance, R.string.common_error__unknown_error),
    CUSTOM(R.drawable.ic_maintenance, R.string.common_error__unknown_error);

}