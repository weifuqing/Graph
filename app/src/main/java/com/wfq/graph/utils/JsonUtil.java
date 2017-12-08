package com.wfq.graph.utils;

import android.view.View;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weifuqing on 2017/8/21 0021.
 */

public class JsonUtil {

    //转成对象
    public static <T> T parseObject(String data,Class<T> clazz){

        T t = null;
        try {
            t = JSON.parseObject(data,clazz);
        }catch (Exception e){
            e.printStackTrace();
            Gson  gson = new Gson();
            t = gson.fromJson(data,clazz);
        }
        return t;
    }

    //生成json数据
    public static <T> String parseJson(T t){
        Gson gson = new Gson();
        return gson.toJson(t);
    }

    public static <T> ArrayList<T> parseList(String data,Class<T> clazz){
        return (ArrayList<T>) JSON.parseArray(data,clazz);
    }
}
