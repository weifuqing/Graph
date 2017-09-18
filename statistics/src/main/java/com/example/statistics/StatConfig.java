package com.example.statistics;

import android.content.Context;
import android.content.Intent;

import com.example.statistics.bean.StatReportStrategy;
import com.example.statistics.service.TimeService;
import com.example.statistics.utils.PollingUtil;

/**
 * Created by weifuqing on 2017/8/10 0010.
 */

public class StatConfig {

    private static Context mContext;
    private static StatReportStrategy mStrategy;

    public static String FILE_PATH = "/stat/log";

    public static void init(Context context, StatReportStrategy strategy){
        mContext = context;
        mStrategy = strategy;
        mContext.startService(new Intent(mContext, TimeService.class));
        StatService.launch();
    }

    public static Context getContext(){
        return mContext;
    }

    public static StatReportStrategy getStrategy(){
        return mStrategy;
    }

}
