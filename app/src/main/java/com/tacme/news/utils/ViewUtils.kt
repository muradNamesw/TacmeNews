package com.tacme.news.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import tabadul.fasah.model.enums.ErrorType
import com.tacme.news.R
import com.tacme.news.app.MyApplication
import com.tacme.news.helpers.DownloadError
import com.tacme.news.helpers.NetworkHandler
import com.tacme.news.networking.WebServiceError
import com.tacme.news.view.dialog.ErrorDialog


object ViewUtils {

    /***
     * Tool-Bar
     */
    var toolbarHeight = -1







    fun handleError(activity:  Activity?, error: WebServiceError) {

        when (error) {

            WebServiceError.serverProblem -> serverError(activity)

            WebServiceError.noInternetConnection -> noConnection(activity)




            /*
            WebServiceError.noImporterFoundByThisNumber -> {
                showDialog(
                    activity,
                    R.string.common_error__title,
                    R.string.common_error__importer_not_registered
                )
            }
            WebServiceError.noAuthorizationFound -> {
                showDialog(
                    activity,
                    R.string.common_error__title,
                    R.string.common_error__no_available_authorization
                )
            }
            WebServiceError.customBrokerNotRegisteredInThePort -> {
                showDialog(
                    activity,
                    R.string.common_error__title,
                    R.string.common_error__custom_broker_not_registered
                )

            }
            WebServiceError.authorizationAlreadyExist -> {
                showDialog(
                    activity,
                    R.string.common_error__title,
                    R.string.common_error__authorization_already_existed
                )
            }
            WebServiceError.startDateShouldBeBeforeEndDate -> {
                showDialog(
                    activity,
                    R.string.common_error__title,
                    R.string.common_error__start_date_should_be_before_end_date
                )
            }
            WebServiceError.customBrokerNotFound -> {
                showDialog(
                    activity,
                    R.string.common_error__title,
                    R.string.common_error__custom_broker_not_found
                )
            }
            WebServiceError.otpAlreadySent -> {
                showDialog(
                    activity,
                    R.string.common_error__title,
                    R.string.common_error__otp_already_sent
                )
            }
            WebServiceError.otpNotValid -> {
                showDialog(
                    activity,
                    R.string.common_error__title,
                    R.string.common_error__otp_not_valid
                )

            }
            WebServiceError.noAuthorizationFoundToCancel -> {
                showDialog(
                    activity,
                    R.string.common_error__title,
                    R.string.common_error__no_authorization_found_to_cancel
                )
            }
            WebServiceError.noResults -> {
                showDialog(
                    activity,
                    R.string.common_error__title,
                    R.string.common_error__no_data
                )
            }


            WebServiceError.portRegister -> {
                showDialog(
                    activity,
                    R.string.common_error__title,
                    R.string.common_error__port_already_registered
                )
            }


            WebServiceError.importerTypeNotBelongToCountry -> {
                showDialog(
                    activity,
                    R.string.common_error__title,
                    R.string.common_error__register_importer__importer_type_not_belong_to_country
                )
            }

            WebServiceError.customDeclarationNotBelongToThisBranch -> {
                showDialog(
                    activity,
                    R.string.common_error__title,
                    R.string.common_error__custom_declaration_not_belong_to_this_branch
                )
            }

            WebServiceError.customDeclarationNotRegisteredInTheCustoms -> {
                showDialog(
                    activity,
                    R.string.common_error__title,
                    R.string.common_error__custom_declaration_not_registered_in_the_customs
                )
            }

            WebServiceError.importerAlreadyRegisteredPort -> {
                showDialog(
                    activity,
                    R.string.common_error__title,
                    R.string.common_error__importer_already_registered_in_port
                )
            }
            WebServiceError.noImporterInfoFound -> {
                ErrorDialog(
                    activity ?: MyApplication.instance?.applicationContext!!,
                    activity?.resources?.getString(R.string.select_branch_activity__message_no_importer_info)
                        ?: ""

                    , {
                        PreferencesHelper.getInstance().logout()
                        activity?.startActivity(
                            Intent(
                                activity,
                                LoginActivity::class.java
                            )
                        )
                        activity?.finish()

                    }, {
                        PreferencesHelper.getInstance().logout()
                        activity?.startActivity(
                            Intent(
                                activity,
                                LoginActivity::class.java
                            )
                        )
                        activity?.finish()
                    }).show()
            }
            */
            else -> {
                serverError(activity)
            }
        }
    }

    fun showDialog(activity: Activity?, title: Int, message: Int) {
        val alertDialog: AlertDialog =
            AlertDialog.Builder(activity ?: MyApplication.instance?.applicationContext!!).create()
        alertDialog.setTitle(title)
        alertDialog.setMessage(activity?.getString(message))
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, activity?.getString(R.string.ok)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()
    }

    fun showDialog(activity: Activity?, message: String) {
        val alertDialog: AlertDialog =
            AlertDialog.Builder(activity ?: MyApplication.instance?.applicationContext!!).create()
        alertDialog.setTitle("")
        alertDialog.setMessage(message)
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, activity?.getString(R.string.ok)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()
    }

    fun showMessageCorrect(mActivity: Activity, message: String?) {

        //Toast.makeText(applicationContext.getApplicationContext(), message, Toast.LENGTH_LONG).show();

        val builder = android.app.AlertDialog.Builder(mActivity)

        //builder.setTitle(mActivity.getString(R.string.info));
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton( mActivity.getString(R.string.ok) ) { dialog, which ->
            //builder.dim
        }

        builder.show()

        /*
        Cue.init()
                .with(applicationContext)
                .setMessage(message)
                .setType(Type.SUCCESS)
                .show()
        */

        /*
        CookieBar.build(applicationContext)
                .setTitle(applicationContext.getString(R.string.info))         // String resources are also supported
                .setMessage(message)     // i.e. R.string.message
                .setCookiePosition(CookieBar.TOP)
                .setBackgroundColor(R.color.green_circle)
                .setIcon(R.drawable.ic_correct)
                .setIconAnimation(R.animator.iconspin)
                .show()
        */
    }



    fun serverError(activity: Activity?) {

        ErrorDialog(
            activity ?: MyApplication.instance?.applicationContext!!,
            ErrorType.SERVER_ERROR
        ) {
            NetworkHandler.retryRequest()

        }.show()
    }

    fun noConnection(activity: Activity?) {

        ErrorDialog(
            activity ?: MyApplication.instance?.applicationContext!!,
            ErrorType.NO_INTERNET
        ) {
            NetworkHandler.retryRequest()
        }.show()
    }



}
