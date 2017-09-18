package com.example.statistics;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by weifuqing on 2017/7/21 0021.
 */

public abstract class HttpCallback<T> {

    private Class clazz;
    private boolean isList;

    public <T> HttpCallback( Class<T> clazz, boolean isList){
        this.clazz = clazz;
        this.isList = isList;
    }

    public Class<T> getT_class(){
        return clazz;
    }

    public boolean isList(){
        return isList;
    }

    public void onSuccess(T t){

    }

    public void onSuccess(ArrayList<T> list){

    }

    public void onFailure(int errorCode, String message){

    }
}
