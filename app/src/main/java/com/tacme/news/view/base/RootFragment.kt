package com.tacme.news.view.base

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.Fragment

open class RootFragment : Fragment(){

     open fun showQDialog(
        message: Int, positiveTitle: Int, negativeTitle: Int,
        positiveOnClickListener: DialogInterface.OnClickListener
    ) {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(activity)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton(
            positiveTitle
        ) { dialog, arg1 ->
            dialog.dismiss()
            positiveOnClickListener.onClick(dialog, arg1)
        }
         alertDialogBuilder.setNegativeButton(
            negativeTitle
        ) { dialog, which -> dialog.dismiss() }
         val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    companion object {

        const val LIST_PARCELABLE = "LIST_PARCELABLE"
        const val PARCELABLE      = "PARCELABLE"
        const val SERIALIZABLE    = "SERIALIZABLE"
        const val PARCELABLE_2    = "PARCELABLE_2"

        const val PARCELABLE_ARRAY   = "PARCELABLE_ARRAY"
        const val PARCELABLE_ARRAY_2 = "PARCELABLE_ARRAY_2"

        const val PARAM_IS_REJECTED_KEY = "rejected"
        const val PARAM_REF_NO_KEY      = "refNo"
        const val PARAM_MESSAGE_ID_KEY  = "MESSAGE_ID_KEY"

        const val PARAM_AUTH_DEC        = "authDec"
        const val PARAM_EXCISE_DETAILS  = "exciseDetails"

        const val PARAM_PLEDGE_ITEM_FROM_DATE = "PLEDGE_ITEM_FROM_DATE"
        const val PARAM_PLEDGE_ITEM_TO_DATE   = "PLEDGE_ITEM_TO_DATE"
        const val PARAM_PLEDGE_ITEM_PORT      = "PLEDGE_ITEM_PORT"
        const val PARAM_PLEDGE_ITEM_TYPE      = "PLEDGE_ITEM_TYPE"

        const val PARAM_PLEDGE_ITEM_DETAILS_OBJ  = "PLEDGE_ITEM_DETAILS_OBJ"

        const val PARAM_TAX_DETAILS_OBJ          = "TAX_DETAILS_OBJ"

        const val PARAM_EXEMPTION_TYPE           = "EXEMPTION_TYPE"

        const val PARAM_FINE_DETAILS_WAIVERS     = "FINE_DETAILS_WAIVERS"
        const val PARAM_FINE_DETAILS_CASES       = "FINE_DETAILS_CASES"
        const val PARAM_FINE_DETAILS_COLL_ORDERS = "FINE_DETAILS_COLL_ORDERS"

        const val PARAM_APPOINTMENT_DETAILS_APPOINTMENT_NO = "APPOINTMENT_DETAILS_APPOINTMENT_NO"
        const val PARAM_DRIVER_DETAILS_DRIVER_ID           = "DRIVER_DETAILS_DRIVER_ID"
        const val PARAM_TRUCK_DETAILS_PLATE_NO             = "TRUCK_DETAILS_PLATE_NO"
        const val PARAM_TRANS_ORDER_DETAILS_TRANS_OBJ      = "TRANS_ORDER_DETAILS_TRANS_OBJ"
        const val PARAM_TRANS_ORDER_DETAILS_TRANS_ORDER_NO = "TRANS_ORDER_DETAILS_TRANS_ORDER_NO"


        const val PARAM_PLEDGE_NOT_TO_ACT_DEC_DATE   = "PLEDGE_NOT_TO_ACT_DEC_DATE"
        const val PARAM_PLEDGE_NOT_TO_ACT_DEC_TYPE   = "PLEDGE_NOT_TO_ACT_DEC_TYPE"
        const val PARAM_PLEDGE_NOT_TO_ACT_DEC_NUMBER = "PLEDGE_NOT_TO_ACT_DEC_NUMBER"
        const val PARAM_PLEDGE_NOT_TO_ACT_PORT       = "PLEDGE_NOT_TO_ACT_PORT"

        const val PARAM_PLEDGE_NOT_TO_ACT_DEC_TYPE_NAME = "PLEDGE_NOT_TO_ACT_DEC_TYPE_NAME"
        const val PARAM_PLEDGE_NOT_TO_ACT_PORT_NAME     = "PLEDGE_NOT_TO_ACT_PORT_NAME"
        const val PARAM_RESULT_MESSAGE                  = "RESULT_MESSAGE"


        const val PLEDGE_NOT_TO_ACT_DEC_NUMBER          = "PLEDGE_NOT_TO_ACT_DEC_NUMBER"
        const val PLEDGE_NOT_TO_ACT_DEC_DATE            = "PLEDGE_NOT_TO_ACT_DEC_DATE"
        const val PLEDGE_NOT_TO_ACT_DEC_TYPE            = "PLEDGE_NOT_TO_ACT_DEC_TYPE"
        const val PLEDGE_NOT_TO_ACT_PORT                = "PLEDGE_NOT_TO_ACT_PORT"

        const val PLEDGE_NOT_TO_ACT_DEC_TYPE_NAME       = "PLEDGE_NOT_TO_ACT_DEC_TYPE_NAME"
        const val PLEDGE_NOT_TO_ACT_PORT_NAME           = "PLEDGE_NOT_TO_ACT_PORT_NAME"

        const val PLEDGE_NOT_TO_ACT_LATITUDE            = "PLEDGE_NOT_TO_ACT_LATITUDE"
        const val PLEDGE_NOT_TO_ACT_LONGITUDE           = "PLEDGE_NOT_TO_ACT_LONGITUDE"

    }

}
