package com.example.module.libBase;

import android.content.Context;

public class TokenManager {
    private static final String PREFS_NAME = "loginInfo";
    private static final String KEY_TOKEN = "userToken";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    public static void saveToken(Context context, String token) {
        SPUtils.putString(context, KEY_TOKEN, token);
        SPUtils.putBoolean(context, KEY_IS_LOGGED_IN, true);
    }

    public static boolean getLoginStatus(Context context) {
        return SPUtils.getBoolean(context, KEY_IS_LOGGED_IN, false);
    }

    public static String getToken(Context context) {
        return  SPUtils.getString(context, KEY_TOKEN);
    }

    public static void clearToken(Context context) {
        SPUtils.clear(context);
    }
}
