package com.wfq.graph.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

//打开或关闭软键盘
public class KeyBoardUtil
{
    /**
     * 打卡软键盘s
     */
    public static void openKeybord(View view)
    {
        Context mContext=view.getContext();
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
 
    /**
     * 关闭软键盘
     *
     */
    public static void closeKeybord(View view)
    {
        Context mContext=view.getContext();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}