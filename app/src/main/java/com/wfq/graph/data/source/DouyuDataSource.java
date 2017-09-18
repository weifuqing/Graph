package com.wfq.graph.data.source;

import android.app.Activity;
import android.content.Context;

import com.wfq.graph.app.HttpCallback;
import com.wfq.graph.data.bean.douyu.RoomInfo;
import com.wfq.graph.data.bean.douyu.Rooms;

import java.util.List;

/**
 * Created by weifuqing on 2017/7/10 0010.
 */

public interface DouyuDataSource {

    interface LoadDouyuCallback{
        void onRoomLoaded(Rooms rooms);
        void onDataNotAvailable();
    }
//    获取直播房间列表信息/api/RoomApi/live/{分类 ID 或者分类别名}
    void getRooms(Context context, String id, int offset, int limit, HttpCallback callback);

}
