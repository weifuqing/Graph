package com.wfq.graph.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.statistics.StatService;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.wfq.graph.R;
import com.wfq.graph.base.BaseFragment;
import com.wfq.graph.base.BaseLazyFragment;
import com.wfq.graph.data.bean.douyu.RoomInfo;
import com.wfq.graph.ui.live.LiveWebActivity;
import com.wfq.graph.utils.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by weifuqing on 2017/6/8 0008.
 */

public class LiveFragment extends BaseFragment implements RecyclerArrayAdapter.OnLoadMoreListener, LiveContract.View, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_room)
    EasyRecyclerView rvRoom;
    @BindView(R.id.network_error_layout)
    ViewStub networkErrorLayout;

    private View networkErrorView;

    private List<RoomInfo> roomInfos = new ArrayList<>();
    private RoomAdapter roomAdapter;

    private LivePresenter livePresenter;
    private int page = 0;
    private int offset = 0;
    private int limit = 10;
    private boolean hasNext = true;
    private String id = "DOTA2";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live;
    }

    @Override
    protected void initView() {
        livePresenter = new LivePresenter(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvRoom.setLayoutManager(staggeredGridLayoutManager);
        roomAdapter = new RoomAdapter(getActivity());

        rvRoom.setAdapterWithProgress(roomAdapter);

        roomAdapter.setMore(R.layout.layout_load_more, this);
        roomAdapter.setError(R.layout.layout_error);
        roomAdapter.setNoMore(R.layout.layout_no_more);

        rvRoom.setRefreshListener(this);
    }

    @Override
    protected void initListener() {
        roomAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(ConstantUtil.ROOM_DOUYU,roomInfos.get(position));
                gotoActivity(LiveWebActivity.class,bundle);
            }
        });
    }

    @Override
    protected void initData() {
        livePresenter.getRooms(getActivity(),getActivity(),id, offset, limit, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.startPage("直播列表");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.endPage("直播列表");
    }

    @Override
    public void onRefresh() {
        page = 0;
        offset = 0;
        livePresenter.getRooms(getActivity(),getActivity(),id, offset, limit, true);
    }

    @Override
    public void onLoadMore() {
        if(hasNext) {
            page++;
            offset = page*limit;
            livePresenter.getRooms(getActivity(),getActivity(), id, offset, limit, false);
        }
    }

    @Override
    public void refresh(List<RoomInfo> datas) {
        roomInfos.clear();
        roomInfos.addAll(datas);
        roomAdapter.clear();
        roomAdapter.addAll(datas);
    }

    @Override
    public void load(List<RoomInfo> datas) {
        if(datas==null||datas.size()==0){
            hasNext = false;
            return;
        }
        roomInfos.addAll(datas);
        roomAdapter.addAll(datas);
    }

    @Override
    public void showError() {
        rvRoom.showError();
        if(networkErrorView!=null){
            networkErrorView.setVisibility(View.VISIBLE);
            return;
        }
        networkErrorView = networkErrorLayout.inflate();
    }

    @Override
    public void showNormal() {
        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.GONE);
        }
    }



    class RoomAdapter extends RecyclerArrayAdapter<RoomInfo> {

        public RoomAdapter(Context context) {
            super(context);
        }

        @Override
        public void OnBindViewHolder(BaseViewHolder holder, int position) {
            super.OnBindViewHolder(holder, position);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new RoomViewHolder(parent, R.layout.item_live);
        }
    }

    class RoomViewHolder extends BaseViewHolder<RoomInfo>{

        private ImageView iv_room;
        private ImageView iv_head;
        private TextView tv_name;
        private TextView tv_num;

        public RoomViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            iv_room = $(R.id.iv_room);
            iv_head = $(R.id.iv_head);
            tv_num = $(R.id.tv_num);
            tv_name = $(R.id.tv_name);
        }

        @Override
        public void setData(RoomInfo data) {
            super.setData(data);

            Glide.with(getContext())
                    .load(data.getRoom_src())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(iv_room);

            Glide.with(getContext())
                    .load(data.getAvatar_mid())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(iv_head);

            tv_num.setText(data.getOnline()+"");
            tv_name.setText(data.getRoom_name());
            tv_name.setSelected(true);

        }
    }
}
