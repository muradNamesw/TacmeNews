
package com.tacme.news.view.activities.base

//
//  RootActivity.java
//
//  Created by Ahmad on 2/14/18.
//  Copyright © 2018. All rights reserved.

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.tacme.news.networking.GlobalKeys
import com.tacme.news.utils.PreferencesHelper
import java.util.*

open class RootActivity : AppCompatActivity() {


    open fun loadLanguage() {
        val resources = resources
        val configuration = resources.configuration
        val locale =
            Locale(
                PreferencesHelper.getInstance()[GlobalKeys.kAppLanguage, GlobalKeys.kDefaultLang]
            )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            applicationContext.createConfigurationContext(configuration)

        } else {
            val displayMetrics = resources.displayMetrics
            configuration.locale=locale

            resources.updateConfiguration(configuration, displayMetrics)
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(updateBaseContextLocale(base))
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (overrideConfiguration != null) {
//            val uiMode = overrideConfiguration.uiMode
            overrideConfiguration.setTo(baseContext.resources.configuration)
            overrideConfiguration.uiMode = Configuration.UI_MODE_TYPE_NORMAL
        }

        super.applyOverrideConfiguration(overrideConfiguration)
    }

    open fun updateBaseContextLocale(context: Context): Context? {
        val language: String = PreferencesHelper.getInstance()[GlobalKeys.kAppLanguage, GlobalKeys.kDefaultLang] ?: GlobalKeys.kDefaultLang

        val locale = Locale(language)
        Locale.setDefault(locale)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResourcesLocale(context, locale)
        } else updateResourcesLocaleLegacy(context, locale)
    }

    @TargetApi(Build.VERSION_CODES.N)
    open fun updateResourcesLocale(context: Context, locale: Locale): Context? {

        val configuration = context.resources.configuration
        configuration.setLocale(locale)

        return context.createConfigurationContext(configuration)
    }

    open fun updateResourcesLocaleLegacy(context: Context, locale: Locale): Context? {

        val resources = context.resources
        val configuration = resources.configuration

        configuration.locale=locale

        resources.updateConfiguration(configuration, resources.displayMetrics)

        return context
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_DOWN) {
            val view = this.currentFocus
            if (view != null) {
                val imm =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
        return try {
            super.dispatchTouchEvent(event)
        } catch (e: Exception) {
            false
        }
    }

}