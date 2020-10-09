package com.tacme.news.app

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import androidx.multidex.MultiDexApplication
import com.tacme.news.networking.GlobalKeys
import com.tacme.news.networking.GlobalKeys.kAppLanguage
import com.tacme.news.networking.GlobalKeys.kDefaultLang
import com.tacme.news.utils.PreferencesHelper
import com.tacme.news.utils.PreferencesHelper.Companion.initInstance
import java.util.*


class MyApplication : MultiDexApplication() {

    var preferences: SharedPreferences? = null

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        initInstance(applicationContext)

        val preferencesHelper = PreferencesHelper.getInstance()

        var lang = preferencesHelper[kAppLanguage, ""]

        if (lang == "") {
            lang = Locale.getDefault().language.toLowerCase()
        }

        preferencesHelper[kAppLanguage] = lang!!
        preferences = PreferenceManager.getDefaultSharedPreferences(instance)

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(updateBaseContextLocale(base))
    }

    private fun updateBaseContextLocale(context: Context): Context? {

        val language: String = context.getSharedPreferences(GlobalKeys.kPreferencesKey, 0).getString(
                kAppLanguage,
                kDefaultLang
            ) ?: kDefaultLang

        val locale = Locale(language)
        Locale.setDefault(locale)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResourcesLocale(context, locale)
        } else updateResourcesLocaleLegacy(context, locale)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResourcesLocale(context: Context, locale: Locale): Context? {

        val configuration = context.resources.configuration

        configuration.setLocale(locale)

        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLocaleLegacy(context: Context, locale: Locale): Context? {

        val resources = context.resources
        val configuration = resources.configuration

        configuration.locale = locale

        resources.updateConfiguration(configuration, resources.displayMetrics)

        return context
    }


    companion object {
        var instance: MyApplication? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

    }
}