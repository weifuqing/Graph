package com.wfq.graph.data.source.remote;

import android.content.Context;

import com.wfq.graph.app.BaseRequest;
import com.wfq.graph.app.HttpCallback;
import com.wfq.graph.data.bean.douyu.RoomInfo;
import com.wfq.graph.data.bean.douyu.Rooms;
import com.wfq.graph.data.source.DouyuDataSource;
import com.wfq.graph.http.BaseUrl;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by weifuqing on 2017/7/10 0010.
 */

public class RemoteDouyuDataSource extends BaseRequest implements DouyuDataSource {
    @Override
    public void getRooms(Context context,String id, int offset, int limit, HttpCallback callback) {

        Map<String,String> map = new HashMap<>();
        map.put("offset",offset+"");
        map.put("limit",limit+"");
        requestGet(context, BaseUrl.douyu_rooms + id, map,callback);

    }
}
