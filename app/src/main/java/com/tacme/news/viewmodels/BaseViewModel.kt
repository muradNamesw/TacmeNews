package com.tacme.news.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.tacme.news.app.MyApplication
import com.tacme.news.networking.GlobalKeys
import java.util.*

/**
 * Created by Murad Adnan on 2019-11-26.
 */
open class BaseViewModel : AndroidViewModel {

    constructor(application: Application) : super(application)

    open fun getTitleString(src: Int): Int {
    return src
        
        
//        val context: Context = MyApplication.applicationContext()
//
//        val language: String = context.getSharedPreferences(GlobalKeys.kPreferencesKey, 0).getString(
//                                    GlobalKeys.kAppLanguage,
//                                    GlobalKeys.kDefaultLang
//                                ) ?: GlobalKeys.kDefaultLang
//
//        val locale = Locale(language)
//        Locale.setDefault(locale)
//
//        val configuration = context.resources.configuration
//
//        configuration.setLocale(locale)
//        configuration.setLayoutDirection(locale)
//
//        return context.createConfigurationContext(configuration).getString(src)

        /*
        val context: Context? = ContextUtil.getUpdatedContext()
        return context?.getString(src) ?: ""
        */

        //return MyApplication.instance?.applicationContext?.getString(src) ?: ""
        //return MyApplication.instance?.getString(src) ?: ""
        //return getApplication<Application>().resources.getString(src)
    }

    open fun getString(src: Int): String {
        val context: Context = MyApplication.applicationContext()

        val language: String = context.getSharedPreferences(GlobalKeys.kPreferencesKey, 0).getString(
                                    GlobalKeys.kAppLanguage,
                                    GlobalKeys.kDefaultLang
                                ) ?: GlobalKeys.kDefaultLang

        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration

        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration).getString(src)
    }
}