package com.wfq.graph.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by weifuqing on 2017/6/24 0024.
 */

public class NetworkBroadcastReceiver extends BroadcastReceiver {
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
