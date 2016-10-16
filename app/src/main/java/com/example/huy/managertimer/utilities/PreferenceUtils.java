package com.example.huy.managertimer.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {

    private static final String PREFERENCES_NAME = "com.example.25min";

    public static void setValue(Context ctx, String key, String val) {
        if (ctx != null) {
            SharedPreferences pref = ctx.getSharedPreferences(PREFERENCES_NAME, 0);
            SharedPreferences.Editor edit = pref.edit();
            edit.putString(key, val);
            edit.apply();
        }
    }

    public static void setValue(Context ctx, String key, int val) {
        if (ctx != null) {
            SharedPreferences pref = ctx.getSharedPreferences(PREFERENCES_NAME, 0);
            SharedPreferences.Editor edit = pref.edit();
            edit.putInt(key, val);
            edit.apply();
        }
    }

    public static void setValue(Context ctx, String key, boolean val) {
        if (ctx != null) {
            SharedPreferences pref = ctx.getSharedPreferences(PREFERENCES_NAME, 0);
            SharedPreferences.Editor edit = pref.edit();
            edit.putBoolean(key, val);
            edit.apply();
        }
    }

    public static boolean getValue(Context ctx, String key, boolean delVal) {
        if (ctx != null) {
            SharedPreferences pref = ctx.getSharedPreferences(PREFERENCES_NAME, 0);
            return pref.getBoolean(key, delVal);
        }
        return delVal;
    }

    public static int getValue(Context ctx, String key, int delVal) {
        if (ctx != null) {
            SharedPreferences pref = ctx.getSharedPreferences(PREFERENCES_NAME, 0);
            return pref.getInt(key, delVal);
        }
        return delVal;
    }

    public static String getValue(Context ctx, String key, String delVal) {
        if (ctx != null) {
            SharedPreferences pref = ctx.getSharedPreferences(PREFERENCES_NAME, 0);
            return pref.getString(key, delVal);
        }
        return delVal;
    }

    public static void removeValue(Context ctx, String key) {
        if (ctx != null) {
            SharedPreferences settings = ctx.getSharedPreferences(PREFERENCES_NAME, 0);
            SharedPreferences.Editor edit = settings.edit();
            edit.remove(key).apply();
        }
    }
}
