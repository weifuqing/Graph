package com.wfq.graph.ui.main;

import android.app.Activity;
import android.content.Context;

import com.wfq.graph.app.HttpCallback;
import com.wfq.graph.base.BasePresenter;
import com.wfq.graph.data.bean.ArrayResult;
import com.wfq.graph.data.bean.douyu.RoomInfo;
import com.wfq.graph.data.bean.douyu.Rooms;
import com.wfq.graph.data.source.DouyuDataSource;
import com.wfq.graph.data.source.DouyuRepository;
import com.wfq.graph.utils.ToastUitl;

import java.util.ArrayList;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by weifuqing on 2017/7/12 0012.
 */

public class LivePresenter implements LiveContract.Presenter {

    private LiveContract.View mView;
    private DouyuRepository douyuRepository;

    public LivePresenter(LiveContract.View view) {
        mView = view;
        douyuRepository = new DouyuRepository();
    }

    @Override
    public void getRooms(Context context, Activity activity, String id, final int offset, int limit, final boolean isRefresh) {
        douyuRepository.getRooms(context, id, offset, limit, new HttpCallback<Rooms>(activity, Rooms.class, true, true) {

            @Override
            public void onSuccess(ArrayList list) {
                super.onSuccess(list);
                if (isRefresh) {
                    mView.refresh(list);
                } else {
                    mView.load(list);
                }
                mView.showNormal();
            }

            @Override
            public void onSuccess(Rooms rooms) {
                super.onSuccess(rooms);
                if (rooms.getError() != 0) {
                    ToastUitl.show("访问失败：" + rooms.getError());
                    if (isRefresh)
                        mView.showError();
                    return;
                }
                if (isRefresh) {
                    mView.refresh(rooms.getData());
                } else {
                    mView.load(rooms.getData());
                }
                mView.showNormal();
            }

            @Override
            public void onFailure(int errorCode, String message) {
                super.onFailure(errorCode, message);
                mView.showError();
            }

        });
    }

    @Override
    public void start() {
    }
}
