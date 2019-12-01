package com.qzc.customdialog.utils;

import android.widget.EditText;

import androidx.annotation.Nullable;

/**
 * created by qinzhichang at 2019/12/01 11:18
 * desc:
 */
public class StringUtils {

    private StringUtils() {
    }

    public static String EMPTY_STRING = "";

    public static String valueOf(Object value) {
        return value != null ? value.toString() : EMPTY_STRING;
    }

    public static boolean isEmpty(@Nullable String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isEmpty(String... strings) {
        for (String str : strings) {
            if (str == null || str.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static String getEditTextContent(EditText editText) {
        if (editText == null) {
            return null;
        } else {
            return editText.getText().toString().trim();
        }
    }
}
