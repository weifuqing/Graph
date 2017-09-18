package com.example.statistics.bean;

/**
 * Created by weifuqing on 2017/8/10 0010.
 */

public enum StatReportStrategy {

    INSTANT(1),//即时
    BATCH(2),//批量
    APP_LAUNCH(3),//启动
    DAILY(4);

    int type;

    private StatReportStrategy(int type){
        this.type = type;
    }

}
