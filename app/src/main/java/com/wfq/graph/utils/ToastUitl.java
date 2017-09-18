package com.wfq.graph.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.wfq.graph.app.MyApplication;

import java.util.Comparator;

/**
 * Toast统一管理类
 */
public class ToastUitl {

    private static Toast mToast;
    private static Context context = MyApplication.getContext();

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort( CharSequence message) {
        if (context == null) return;
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param strResId
     */
    public static void showShort(int strResId) {
        if (context == null) return;
        Toast.makeText(context, context.getResources().getText(strResId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong( CharSequence message) {
        if (context == null) return;
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param strResId
     */
    public static void showLong(int strResId) {
        if (context == null) return;
        Toast.makeText(context, context.getResources().getText(strResId), Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        if (context == null) return;
        Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param strResId
     * @param duration
     */
    public static void show( int strResId, int duration) {
        if (context == null) return;
        Toast.makeText(context, context.getResources().getText(strResId), duration).show();
    }

    /**
     * 显示不重复
     * @param message
     */
    public static void show(CharSequence message) {

        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    public static void showSnackbar(View view,CharSequence message){
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();
    }
}
