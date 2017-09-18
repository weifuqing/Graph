package com.example.statistics.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by weifuqing on 2017/8/28 0028.
 */

public class SpUtil {

    private static String SP_NAME = "statistics";

    public static void putString(Context context,String key,String value){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getString(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }

    public static void putLong(Context context,String key,long value){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key,value);
        editor.commit();
    }

    public static long getLong(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        return sp.getLong(key,0);
    }
}
