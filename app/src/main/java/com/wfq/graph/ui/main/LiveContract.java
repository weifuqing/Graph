package com.wfq.graph.ui.main;

import android.app.Activity;
import android.content.Context;

import com.wfq.graph.base.BasePresenter;
import com.wfq.graph.base.BaseView;
import com.wfq.graph.data.bean.douyu.RoomInfo;
import com.wfq.graph.data.bean.douyu.Rooms;
import com.wfq.graph.data.source.DouyuDataSource;

import java.util.List;

/**
 * Created by weifuqing on 2017/7/12 0012.
 */

public interface LiveContract {

    interface View extends BaseView {
        void refresh(List<RoomInfo> datas);

        void load(List<RoomInfo> datas);

        void showError();

        void showNormal();
    }

    interface Presenter extends BasePresenter {
        void getRooms(Context context, Activity activity, String id, int offset, int limit, boolean isRefresh);
    }
}
