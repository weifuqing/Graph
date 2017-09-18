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

public class GsonUtil {

    //转成对象
    public static <T> T parseObject(String data,Class<T> clazz){
        Gson  gson = new Gson();
        T t = gson.fromJson(data,clazz);
        return t;
    }

    //生成json数据
    public static <T> String parseJson(T t){
        Gson gson = new Gson();
        return gson.toJson(t);
    }

    public static <T> ArrayList<T> parseList(String data,Class clazz){
        return (ArrayList<T>) JSON.parseArray(data,clazz);
    }
}
