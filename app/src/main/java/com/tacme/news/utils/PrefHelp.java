package com.tacme.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;


public class PrefHelp {
    private static SharedPreferences prefs = null;
    private static final String LANG = "lang";
    private static final String USER = "USER";

    private static final String UDID = "UDID";

    private static final String LAST_CHECK_IN = "LAST_CHECK_IN";

    private static final String QUERIES_HISTORY = "QUERIES_HISTORY";

    private static SharedPreferences getPrefs(Context context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        }
        return prefs;
    }

    public static String getLang(Context context) {
        return getPrefs(context).getString(LANG, "en");
    }

    public static Locale getLocale(Context context) {
        return getLang(context).equals("en")?Locale.ENGLISH:new Locale("ar");
    }


    public static void setLang(Context context, String lang) {
        SharedPreferences.Editor prefEditor = getPrefs(context).edit();
        prefEditor.putString(LANG, lang).apply();
    }






    public static boolean isEnglish(Context context) {
        return getLang(context).equals("en");
    }


    public static String getAppLang(Context context) {
        return getPrefs(context).getString(LANG, "en").equals("en") ? "en" : "ar";
    }


    public static void logout(Context context) {
        getPrefs(context).edit().remove(USER).apply();
    }


    public static void setUdid(Context context, String udid) {
        SharedPreferences.Editor prefEditor = getPrefs(context).edit();
        prefEditor.putString(UDID, udid).apply();
    }

    public static String getUdid(Context context) {
        return getPrefs(context).getString(UDID, null);
    }


}
