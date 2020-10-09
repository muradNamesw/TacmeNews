package com.tacme.news.utils;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;

import java.util.List;

import com.tacme.news.R;



/**
 * Created by Eng Murad Ibraheim on 3/20/17.
 */

public class UtilityProject {


    public static final String LANGUAGE = "LANGUAGE_PROJECT_TACME_SA";
    public static final String USER_NAME = "USER_NAME_TACME_SA";
    public static final String NAME = "NAME_LOGIN_TACME_SA";
    public static final String TOKEN_LOGIN = "TOKEN_LOGIN_TACME_SA";
    public static final String LAST_NO = "LAST_NO_TACME_SA";
    public static final String TYPE_LOGIN = "TYPE_LOGIN_TACME_SA";
    public static String LANGUAGE_PAGE = "LANGUAGE_PAGE";
    public static String FIRST_TIME = "FIRST_TIME";
    public static final String USER_TYPE = "USER_APP_TACME_SA";
    public static final String USER_ROLES = "USER_ROLES_TACME_SA";

    public static final String arabic = "\u06f0\u06f1\u06f2\u06f3\u06f4\u06f5\u06f6\u06f7\u06f8\u06f9";

    public static void showMessage(Activity mActivity, String message) {
//        Toast.makeText(applicationContext.getApplicationContext(), message, Toast.LENGTH_LONG).show();
//        Cue.init()
//                .with(applicationContext)
//                .setMessage(message)
//                .setType(Type.PRIMARY)
//                .show();
//        CookieBar.build(applicationContext)
//                .setTitle(applicationContext.getString(R.string.error))         // String resources are also supported
//                .setMessage(message)     // i.e. R.string.message
//                .setCookiePosition(CookieBar.TOP)
//                .setIcon(R.drawable.ic_settings_white_48dp)
//                .setIconAnimation(R.animator.iconspin)
//                .show();
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(mActivity.getString(R.string.error));
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(mActivity.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                builder.dim
            }
        });


        builder.show();


    }

    public static void showMessage(Activity mActivity, String message, String tittle) {
//        Toast.makeText(applicationContext.getApplicationContext(), message, Toast.LENGTH_LONG).show();
//        Cue.init()
//                .with(applicationContext)
//                .setMessage(message)
//                .setType(Type.PRIMARY)
//                .show();

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(tittle);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(mActivity.getString(R.string.done), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                builder.dim
            }
        });
        builder.show();
//        CookieBar.build(applicationContext)
//                .setTitle(tittle)         // String resources are also supported
//                .setMessage(message)     // i.e. R.string.message
//                .setCookiePosition(CookieBar.TOP)
//                .setBackgroundColor(R.color.colorSignUp)
//                .setIcon(R.drawable.ic_i)
//                .setIconAnimation(R.animator.iconspin)
//                .show();

    }

    public static void showMessageCorrect(Activity mActivity, String message) {
//        Toast.makeText(applicationContext.getApplicationContext(), message, Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
//        builder.setTitle(mActivity.getString(R.string.info));
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(mActivity.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                builder.dim
            }
        });


        builder.show();
//        Cue.init()
//                .with(applicationContext)
//                .setMessage(message)
//                .setType(Type.SUCCESS)
//                .show();
//        CookieBar.build(applicationContext)
//                .setTitle(applicationContext.getString(R.string.info))         // String resources are also supported
//                .setMessage(message)     // i.e. R.string.message
//                .setCookiePosition(CookieBar.TOP)
//                .setBackgroundColor(R.color.green_circle)
//                .setIcon(R.drawable.ic_correct)
//                .setIconAnimation(R.animator.iconspin)
//                .show();

    }









    public static boolean isEmptyString(String value) {

        boolean check = false;
        if (value == null || value.toLowerCase().contains("null") || value.isEmpty() || value.toLowerCase().equals("-9")) {
            check = true;
        }
        return check;

    }

    public static boolean isEmptyList(List list) {
        boolean check = false;
        if (list == null && list.size() == 0) {
            check = true;
        }
        return check;
    }



    public static int speed(double distance, int min) {
        int speed = (int) ((float) distance / (float) minToHour(min));
        return speed;
    }

    public static double minToHour(int min) {
        return min / 60;
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    public static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }


}
