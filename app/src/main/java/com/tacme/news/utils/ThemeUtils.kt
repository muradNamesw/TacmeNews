package com.tacme.news.utils

import android.app.Activity

import android.content.Intent
import com.tacme.news.R

object ThemeUtils {

    private val theme: ThemeStyle? = null

    fun changeToTheme(activity: Activity, theme: ThemeStyle) {

        var theme = theme

        theme = theme

        activity.finish()

        activity.startActivity(Intent(activity, activity.javaClass))

    }

    fun onActivityCreateSetTheme(activity: Activity) {

        when (theme) {


            ThemeUtils.ThemeStyle.white -> activity.setTheme(R.style.WhiteStyle)

            ThemeUtils.ThemeStyle.transparent -> activity.setTheme(R.style.TransparentStatusStyle)
        }

    }

    enum class ThemeStyle {
        gradientBlue,
        white,
        transparent
    }

}