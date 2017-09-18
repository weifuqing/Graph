package com.wfq.graph.data.bean.douyu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weifuqing on 2017/7/12 0012.
 */

public class Rooms  {

    private int error;
    private List<RoomInfo> data = new ArrayList<>();

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<RoomInfo> getData() {
        return data;
    }

    public void setData(List<RoomInfo> data) {
        this.data = data;
    }
}
