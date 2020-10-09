package com.tacme.news.components.custom;


/**
 * Created by Eng Murad Ibraheim on 8/11/18.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.tacme.news.R;

public class AspectRatioFrameLayout extends FrameLayout {

    private static final String TAG = "AspectRatioFrameLayout";
    private static final boolean DEBUG = false;

    private float mAspectRatio; // width/height ratio

    public AspectRatioFrameLayout(Context context) {
        super(context);
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        initAttributes(context, attrs);
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initAttributes(context, attrs);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioFrameLayout);

        mAspectRatio = typedArray.getFloat(R.styleable.AspectRatioFrameLayout_aspectRatio, 1.0f);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (DEBUG) Log.d(TAG, "widthMode:"+widthMode+", heightMode:"+heightMode);
        if (DEBUG) Log.d(TAG, "widthSize:"+widthSize+", heightSize:"+heightSize);

        if ( widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY ) {
            // do nothing
        } else if ( widthMode == MeasureSpec.EXACTLY ) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int)(widthSize / mAspectRatio), MeasureSpec.EXACTLY);
        } else if ( heightMode == MeasureSpec.EXACTLY ) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec((int)(heightSize * mAspectRatio), MeasureSpec.EXACTLY);
        } else {
            // do nothing
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}