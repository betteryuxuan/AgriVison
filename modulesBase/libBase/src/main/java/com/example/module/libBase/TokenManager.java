package com.example.module.libBase;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String PREFS_NAME = "loggedInState";
    private static final String KEY_TOKEN = "userToken";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    public static void saveToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(KEY_TOKEN, token);
        edit.putBoolean(KEY_IS_LOGGED_IN, true);
        edit.apply();
    }

    public static boolean getLoginStatus(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public static String getToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sp.getString(KEY_TOKEN, null);
    }

    public static void clearToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(KEY_TOKEN, null);
        edit.putBoolean(KEY_IS_LOGGED_IN, false);
        edit.apply();
    }

}
