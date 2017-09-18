package com.wfq.graph.data.bean;

import java.util.List;

/**
 * Created by weifuqing on 2017/7/3 0003.
 */

public class Result<T> {

    private int error;
    private T data;

    public int getError() {
        return error==0?200:error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
