package com.example.statistics;

import com.example.statistics.bean.Event;
import com.example.statistics.bean.EventType;
import com.example.statistics.utils.AppUtil;
import com.example.statistics.utils.GsonUtil;
import com.example.statistics.utils.LogUtil;

import java.util.Properties;

/**
 * Created by weifuqing on 2017/8/9 0009.
 */

public class StatService {

    private static Event current_event;

    public static void launch(){
        Event event = new Event();
        event.setDeviceId(AppUtil.getDeviceId(StatConfig.getContext()));
        event.setVersionName(AppUtil.getVersionName(StatConfig.getContext()));
        event.setStartTime(System.currentTimeMillis());
        event.setEventType(EventType.LAUNCH);
        sendLog(event);
    }

    //进入页面
    public static void startPage(String pageName) {
        current_event = new Event();
        current_event.setDeviceId(AppUtil.getDeviceId(StatConfig.getContext()));
        current_event.setVersionName(AppUtil.getVersionName(StatConfig.getContext()));
        current_event.setPageName(pageName);
        current_event.setStartTime(System.currentTimeMillis());
        current_event.setEventType(EventType.PAGE);
    }

    //离开页面
    public static void endPage(String pageName) {
        if (current_event.getEventType() == EventType.PAGE&&current_event.getPageName().equals(pageName)){
            current_event.setEndTime(System.currentTimeMillis());
            current_event.setDuration(current_event.getEndTime()-current_event.getStartTime());
            sendLog(current_event);
        }
    }

    //自定义事件
    public static void customEvent(String eventName, Properties properties) {
        Event event = new Event();
        event.setDeviceId(AppUtil.getDeviceId(StatConfig.getContext()));
        event.setVersionName(AppUtil.getVersionName(StatConfig.getContext()));
        event.setEventName(eventName);
        event.setStartTime(System.currentTimeMillis());
        event.setCustom(properties);
        sendLog(event);
    }



    private static void sendLog(Event event){
        switch (StatConfig.getStrategy()){
            case INSTANT:
                SendManager.sendEvents(GsonUtil.parseJson(event));
                break;
            case BATCH:
                LogUtil.saveLog(event);
                break;
            case APP_LAUNCH:
                LogUtil.saveLog(event);
                break;
            case DAILY:
                LogUtil.saveLog(event);
                break;
        }
    }


}
