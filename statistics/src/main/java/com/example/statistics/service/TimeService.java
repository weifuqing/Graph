package com.example.statistics.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import com.example.statistics.StatConfig;
import com.example.statistics.bean.StatReportStrategy;
import com.example.statistics.receiver.TimeChangeReceiver;

/**
 * Created by weifuqing on 2017/8/18 0018.
 */

public class TimeService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(StatConfig.getStrategy()== StatReportStrategy.BATCH) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_TIME_TICK);
            TimeChangeReceiver receiver = new TimeChangeReceiver();
            registerReceiver(receiver, filter);

            //test 注意要删除
            IntentFilter filter1 = new IntentFilter();
            filter.addAction(Intent.ACTION_DATE_CHANGED);
            TimeChangeReceiver receiver1 = new TimeChangeReceiver();
            registerReceiver(receiver1, filter1);
        }else if(StatConfig.getStrategy()==StatReportStrategy.DAILY){
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_DATE_CHANGED);
            TimeChangeReceiver receiver = new TimeChangeReceiver();
            registerReceiver(receiver, filter);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
