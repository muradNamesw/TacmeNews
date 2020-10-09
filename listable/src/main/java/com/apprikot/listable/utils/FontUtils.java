package com.apprikot.listable.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

public class FontUtils {
    public static final String CORBEL_BOLD = "Corbel Bold.ttf";
    public static final String ACCESSORIES = "Aladin-Regular.ttf";

    public enum CustomFont {
        CORBEL_BOLD(FontUtils.CORBEL_BOLD),
        ACCESSORIES(FontUtils.ACCESSORIES);

        private String mFontName;

        CustomFont(String fontName) {
            mFontName = fontName;
        }

        public String getFontName() {
            return mFontName;
        }
    }

    private static Map<CustomFont, Typeface> customFonts;

    public static Typeface getFont(Context context, CustomFont customFont) {
        if (customFonts == null) {
            customFonts = new HashMap<>();
        }

        if (!customFonts.containsKey(customFont) || customFonts.get(customFont) == null) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + customFont.getFontName());
            customFonts.put(customFont, typeface);
        }

        return customFonts.get(customFont);
    }
}