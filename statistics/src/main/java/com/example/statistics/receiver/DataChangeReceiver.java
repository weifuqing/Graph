package com.example.statistics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.statistics.SendManager;
import com.example.statistics.StatConfig;
import com.example.statistics.bean.StatReportStrategy;
import com.example.statistics.utils.LogUtil;

/**
 * Created by weifuqing on 2017/8/29 0029.
 */

public class DataChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (!Intent.ACTION_DATE_CHANGED.equals(intent.getAction())) {
            return;
        }
        if (StatConfig.getStrategy() == StatReportStrategy.DAILY) {
            sendLog(LogUtil.getLog());
        }

    }

    private void sendLog(String log) {
        if (TextUtils.isEmpty(log)) {
            return;
        }
        SendManager.sendEvents(log);
    }
}
