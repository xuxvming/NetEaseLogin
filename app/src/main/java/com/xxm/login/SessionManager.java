package com.xxm.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {

    private static final String IS_LOGIN = "IsLoggedIn";

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static void setLoggedIn(Context context, String userName, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.clear();
        editor.putBoolean(IS_LOGIN, loggedIn);
        editor.apply();
    }

    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(IS_LOGIN, false);
    }

}
