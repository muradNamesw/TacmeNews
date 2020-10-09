package com.apprikot.listable.utils;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.apprikot.listable.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ViewUtils {
    public static void hideKeyboard(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (editText == null) {
                continue;
            }

            editText.clearFocus();
            InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(EditText editText) {
        if (editText == null) {
            return;
        }
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInputFromWindow(editText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
    }

    public static void setConditioned(TextView textView, String text, boolean isValid) {
        if (isValid) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    public static void setWithDefault(TextView textView, String text, String defaultText) {
        boolean isValid = text != null && !text.trim().isEmpty();
        textView.setText(isValid ? text : defaultText);
    }

    public static void animateBackgroundColor(final View v, int colorFrom, int colorTo) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(250);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                v.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }

    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(displayMetrics);
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        // TODO: com.tacme.pass.view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static void loadLogo(ImageView imageView, String url, int stubResId) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.placeholderOf(stubResId).error(stubResId))
                .into(imageView);
    }

    public static void pulse(View v) {
        Animation pulse = AnimationUtils.loadAnimation(v.getContext(), R.anim.pulse);
        v.clearAnimation();
        v.startAnimation(pulse);
    }

    public static void pulseMini(View v) {
        Animation pulse = AnimationUtils.loadAnimation(v.getContext(), R.anim.pulse_mini);
        v.clearAnimation();
        v.startAnimation(pulse);
    }
}
