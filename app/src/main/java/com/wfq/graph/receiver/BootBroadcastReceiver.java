package com.wfq.graph.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wfq.graph.ui.main.MainActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by acer on 2017/6/26.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if(JPushInterface.isPushStopped(context)) {
                JPushInterface.resumePush(context);
            }
        } catch (Exception e) {
        }
    }
}
