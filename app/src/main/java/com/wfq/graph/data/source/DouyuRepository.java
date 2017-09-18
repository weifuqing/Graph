package com.wfq.graph.data.source;

import android.app.Activity;
import android.content.Context;

import com.wfq.graph.app.HttpCallback;
import com.wfq.graph.data.source.local.LocalDouyuDataSource;
import com.wfq.graph.data.source.remote.RemoteDouyuDataSource;

/**
 * Created by weifuqing on 2017/7/10 0010.
 */

public class DouyuRepository implements DouyuDataSource {

    private LocalDouyuDataSource mLocalDouyuDataSource;
    private RemoteDouyuDataSource mRemoteDouyuDataSource;

    public DouyuRepository(){
        mLocalDouyuDataSource = new LocalDouyuDataSource();
        mRemoteDouyuDataSource = new RemoteDouyuDataSource();
    }


    @Override
    public void getRooms(Context context,String id, int offset, int limit, HttpCallback callback) {
        mRemoteDouyuDataSource.getRooms(context,id,offset,limit,callback);
    }
}
