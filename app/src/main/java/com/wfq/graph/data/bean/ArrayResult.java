package com.wfq.graph.data.bean;

import java.util.List;

/**
 * Created by weifuqing on 2017/8/21 0021.
 */

public class ArrayResult<T> {

    private int error;
    private List<T> data;

    public int getError() {
        return error==0?200:error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
