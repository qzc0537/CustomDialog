package com.qzc.customdialog.utils;

import android.util.Log;

import com.qzc.customdialog.BuildConfig;

/**
 * created by qzc at 2019/06/24 16:34
 * desc:
 */
public class LogUtils {

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
    }
}
