package com.wfq.graph.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.umeng.analytics.MobclickAgent;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import io.vov.vitamio.Vitamio;
import okhttp3.OkHttpClient;

/**
 * Created by Admin on 2017/3/22.
 */
public class MyApplication extends Application {

//    RefWatcher refWatcher;

    private static Handler handler;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        handler = new Handler();

        init();
    }

    private void init(){
        //极光
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //友盟
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        //环信
        initHuanxin();
        //leakcanary
//        refWatcher = LeakCanary.install(this);

        //Vitamio播放器
        Vitamio.isInitialized(this);
    }
    private void initHuanxin(){


        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(context, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(AppConfig.isDebug);
    }

    //okhttp
    public static OkHttpClient defaultOkHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .build();
        return client;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static Context getContext() {
        return context;
    }
}
