package com.tacme.news.view.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.ImageView
import tabadul.fasah.model.enums.ErrorType
import com.tacme.news.R
import com.tacme.news.components.custom.CustomTextView

/**
 * Created by Murad Adnan on 2020-01-07.
 */
class ErrorDialog : Dialog, View.OnClickListener {
    private var messageType: ErrorType = ErrorType.NO_INTERNET
    private var onTryAgain: (() -> Unit)? = null
    private var message: String? = null
    private var onClose: (() -> Unit)? = null

    constructor(
        context: Context,
        errorType: ErrorType,
        onTryAgain: (() -> Unit)

    ) : super(context, R.style.BaseDialogTheme) {
        setContentView(R.layout.dialog_error)
        this.messageType = errorType
        this.onTryAgain = onTryAgain
        findView()
    }

    constructor(
        context: Context,
        message: Int,
        onTryAgain: (() -> Unit)


    ) : this(context, ErrorType.CUSTOM, onTryAgain) {
        this.message = context.getString(message)
        findView()
    }

    constructor(
        context: Context,
        message: String,
        onTryAgain: (() -> Unit)

    ) : this(context, ErrorType.CUSTOM, onTryAgain) {
        this.message = message
        findView()

    }

    constructor(
        context: Context,
        message: String,
        onTryAgain: (() -> Unit),
        onClose: (() -> Unit)

    ) : this(context, ErrorType.CUSTOM, onTryAgain) {
        this.onClose=onClose
        this.message = message
        findView()

    }


    private fun findView() {
        window?.attributes?.windowAnimations = R.style.DialogTheme


        val errorTextView = findViewById<CustomTextView>(
            R.id.errorTextView
        )


        val errorImageView = findViewById<ImageView>(
            R.id.errorImageView
        )

        if (message != null) {
            errorTextView.text = message ?: ""
        } else {
            errorTextView.setText(messageType.message)
        }
        errorImageView.setImageResource(messageType.image)


    }

    override fun onClick(v: View?) {

    }
}