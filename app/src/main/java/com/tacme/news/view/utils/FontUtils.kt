package com.tacme.news.view.utils

import android.content.Context
import android.graphics.Typeface
import com.tacme.news.networking.GlobalKeys.kAppLanguage
import com.tacme.news.networking.GlobalKeys.kDefaultLang
import com.tacme.news.utils.PreferencesHelper.Companion.getInstance
import java.util.*

object FontUtils {
    private var customFonts: MutableMap<CustomFont, Typeface>? =
        null

    @JvmStatic
    fun getFont(
        context: Context,
        customFont: CustomFont
    ): Typeface? {
        if (customFonts == null) {
            customFonts =
                HashMap()
        }
        val fontName = if (getInstance()[kAppLanguage, kDefaultLang]
            == "en"
        ) CustomFont.ENGLISH_MEDIUM.fontName else CustomFont.ARABIC_MEDIUM.fontName
        val typeface = Typeface.createFromAsset(context.assets, "fonts/$fontName")
        customFonts!![customFont] = typeface
        return customFonts!![customFont]
    }

    enum class CustomFont(
        val fontName: String
    ) {
        ENGLISH_MEDIUM("lato/Lato-Medium.ttf"),
        ARABIC_MEDIUM("bahe/Bahij_TheSansArabic-Plain.ttf");

    }
}