package com.example.module.libBase;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
    private static final String FILE_NAME = "PREFERENCE_NAME";
    public static final String EMAIL_KEY = "email";
    public static final String USERNAME_KEY = "username";
    public static final String AVATAR_KEY = "avatar";
    public static final String MSGLIST_KEY = "msglist";

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

    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        sp.edit()
                .clear()
                .apply();
    }

    public static void clearMsgList(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        sp.edit()
                .remove(MSGLIST_KEY)
                .apply();
    }

}
