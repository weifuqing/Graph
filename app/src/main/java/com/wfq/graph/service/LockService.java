package com.wfq.graph.service;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.danikula.videocache.service.MusicPlayer;
import com.wfq.graph.ui.lock.LockActivity;

/**
 * Created by weifuqing on 2017/8/23 0023.
 */

public class LockService extends Service {


    private KeyguardManager mKeyguardManager = null;
    private KeyguardManager.KeyguardLock mKeyguardLock = null;
    private BroadcastReceiver mBatInfoReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        stratListenScrrenBroadCast();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopListenScrrenBroadCast();
    }

    private void stratListenScrrenBroadCast() {
        final IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);

        mBatInfoReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                String action = intent.getAction();
                //屏幕亮
                if (Intent.ACTION_SCREEN_ON.equals(action)&& MusicPlayer.isPlaying()) {
                    Intent i = new Intent(context,LockActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                    mKeyguardLock = mKeyguardManager.newKeyguardLock("");
                    mKeyguardLock.disableKeyguard();
                    context.startActivity(i);
                }
                //屏幕灭
                if (Intent.ACTION_SCREEN_OFF.equals(action)&& MusicPlayer.isPlaying()) {
                    Intent i = new Intent(context,LockActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                }
            }
        };
        registerReceiver(mBatInfoReceiver, filter);
    }

    public void stopListenScrrenBroadCast(){
        if(mBatInfoReceiver!=null) {
            unregisterReceiver(mBatInfoReceiver);
        }
    }

}
