package com.wfq.graph.data.bean;

import java.util.List;

/**
 * Created by weifuqing on 2017/7/3 0003.
 */

public class DouyuResult {

    private int error;
    private String data;


    public boolean isSuccess(){
        return error==0||error==200;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
