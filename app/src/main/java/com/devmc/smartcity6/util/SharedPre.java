package com.devmc.smartcity6.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.devmc.smartcity6.CustomApplication;

/**
 * Created by Android Studio.
 * User: Devmc
 * Date: 2022/7/3
 * Time: 10:37
 */
public class SharedPre {
    public static void put(String k, String v) {
        SharedPreferences.Editor s = CustomApplication.context.getSharedPreferences("app", Context.MODE_PRIVATE).edit();
        s.putString(k, v);
        s.apply();
    }

    public static String get(String k, String v) {
        SharedPreferences s = CustomApplication.context.getSharedPreferences("app", Context.MODE_PRIVATE);
        return s.getString(k, v);
    }

    public static void put(String k, boolean v) {
        SharedPreferences.Editor s = CustomApplication.context.getSharedPreferences("app", Context.MODE_PRIVATE).edit();
        s.putBoolean(k, v);
        s.apply();
    }

    public static boolean get(String k, boolean v) {
        SharedPreferences s = CustomApplication.context.getSharedPreferences("app", Context.MODE_PRIVATE);
        return s.getBoolean(k, v);
    }
}
