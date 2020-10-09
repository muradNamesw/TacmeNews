package com.tacme.news.components

import android.content.Context
import com.tacme.news.app.MyApplication
import com.tacme.news.networking.GlobalKeys
import java.util.*


object ContextUtil {

    var langUpdated:   Boolean  = false
    var sharedContext: Context? = null

    fun getUpdatedContext(): Context? {

        if( sharedContext == null ) {

            val context = MyApplication.applicationContext()

            val language: String = context.getSharedPreferences(GlobalKeys.kPreferencesKey, 0).getString(
                GlobalKeys.kAppLanguage,
                GlobalKeys.kDefaultLang
            ) ?: GlobalKeys.kDefaultLang

            val locale = Locale(language)
            Locale.setDefault(locale)

            val configuration = context.resources.configuration

            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)

            context.createConfigurationContext(configuration)

            sharedContext = context

        }

        if( langUpdated ){

            sharedContext?.let { cntxt ->

                val language: String = sharedContext?.getSharedPreferences(GlobalKeys.kPreferencesKey, 0)?.getString(
                    GlobalKeys.kAppLanguage,
                    GlobalKeys.kDefaultLang
                ) ?: GlobalKeys.kDefaultLang

                val locale = Locale(language)
                Locale.setDefault(locale)

                val configuration = sharedContext?.resources?.configuration

                configuration?.setLocale(locale)
                configuration?.setLayoutDirection(locale)

                sharedContext?.createConfigurationContext(configuration)

            }

            langUpdated = false

        }

        sharedContext?.let { return it }

        return null
    }

}
