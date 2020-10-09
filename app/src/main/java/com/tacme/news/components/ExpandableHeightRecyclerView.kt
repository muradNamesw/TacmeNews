package com.tacme.news.components

//
//  ExpandableHeightRecyclerView.java
//
//  Created by Ahmad Abdullah on 5/2/18.
//  Copyright © 2018. All rights reserved.
//

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ExpandableHeightRecyclerView : RecyclerView {

    var isExpanded = false

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // HACK! TAKE THAT ANDROID!

        if (isExpanded) {

            // Calculate entire height by providing a very large height hint.
            // View.MEASURED_SIZE_MASK represents the largest height possible.

            val expandSpec = View.MeasureSpec.makeMeasureSpec(
                View.MEASURED_SIZE_MASK,
                View.MeasureSpec.AT_MOST
            )

            super.onMeasure(widthMeasureSpec, expandSpec)

            val params: ViewGroup.LayoutParams = getLayoutParams()

            params.height = getMeasuredHeight() + 420

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

}