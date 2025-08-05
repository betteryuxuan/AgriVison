package com.example.module.libBase.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
    private static final String FILE_NAME = "PREFERENCE_NAME";
    public static final String EMAIL_KEY = "email";
    public static final String USERNAME_KEY = "username";
    public static final String AVATAR_KEY = "avatar";
    public static final String MSGLIST_1_KEY = "msg1list";
    public static final String MSGLIST_2_KEY = "msg2list";
    public static final String MSGLIST_3_KEY = "msg3list";
    public static final String MSGLIST_4_KEY = "msg4list";
    public static final String POSTNUM_KEY = "postnum";
    public static final String USER_AVATAR_URI_KEY = "useravataruri";
    public static final String CROP_DETAIL_LIST_KEY = "crop_detail_list";

    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        sp.edit()
                .putString(key, value)
                .apply();
    }

    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        sp.edit()
                .putBoolean(key, value)
                .apply();
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        sp.edit()
                .putInt(key, value)
                .apply();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        sp.edit()
                .clear()
                .apply();
    }

    public static void clearMsgList(Context context, int role) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        if (role == 1) {
            sp.edit()
                    .remove(MSGLIST_1_KEY)
                    .apply();
        } else if (role == 2) {
            sp.edit()
                    .remove(MSGLIST_2_KEY)
                    .apply();
        } else if (role == 3) {
            sp.edit()
                    .remove(MSGLIST_3_KEY)
                    .apply();
        } else if (role == 4) {
            sp.edit()
                    .remove(MSGLIST_4_KEY)
                    .apply();
        }
    }

}
