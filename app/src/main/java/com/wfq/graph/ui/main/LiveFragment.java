package com.wfq.graph.ui.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.wfq.graph.R;
import com.wfq.graph.app.HttpCallback;
import com.wfq.graph.app.MyApplication;
import com.wfq.graph.base.BaseFragment;
import com.wfq.graph.data.bean.DouyuResult;
import com.wfq.graph.data.bean.douyu.Game;
import com.wfq.graph.data.bean.douyu.RoomInfo;
import com.wfq.graph.data.source.remote.RemoteDouyuDataSource;
import com.wfq.graph.ui.live.LiveActivity;
import com.wfq.graph.utils.JsonUtil;
import com.wfq.graph.utils.ToastUitl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by weifuqing on 2017/6/8 0008.
 */

public class LiveFragment extends BaseFragment {


    @BindView(R.id.rv_room)
    RecyclerView rvRoom;
    @BindView(R.id.network_error_layout)
    ViewStub networkErrorLayout;
    @BindView(R.id.spinner_game)
    AppCompatSpinner spinner_game;

    private int offset = 0;
    private int limit = 20;
    private boolean hasNext = true;
    private String id = "DOTA2";


    List<RoomInfo> rooms = new ArrayList<>();
    BaseQuickAdapter roomAdapter;
    List<Game> games = new ArrayList<>();
    ArrayAdapter gameAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live;
    }

    @Override
    protected void initView() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
        rvRoom.setLayoutManager(manager);
    }

    private void initSpinner() {

        List<String> list = new ArrayList<>();
        for (Game game:games){
            list.add(game.getGame_name());
        }

        gameAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,list);
        gameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_game.setAdapter(gameAdapter);
        spinner_game.setSelection(getPositionByName("DOTA2"));
    }

    private int getPositionByName(String name){
        for(int i=0;i<games.size();i++){
            if(name.equals(games.get(i).getGame_name())){
                return i;
            }
        }
        return 0;
    }

    @Override
    protected void initListener() {
        spinner_game.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Game game = games.get(i);
                if(id!=game.getCate_id()){
                    id = game.getCate_id();
                    offset = 0;
                    getRooms();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void initData() {
        getGames();
//        getRooms();
    }

    private void getGames() {
        RemoteDouyuDataSource.getGames(getActivity(), new HttpCallback<DouyuResult>(getActivity(), DouyuResult.class, true) {

            @Override
            public void onSuccess(DouyuResult douyuResult) {
                super.onSuccess(douyuResult);
                if (douyuResult.isSuccess()) {
                    games = JsonUtil.parseList(douyuResult.getData(),Game.class);
                    initSpinner();
                } else {
                    ToastUitl.show(douyuResult.getData());
                }
            }

            @Override
            public void onFailure(int errorCode, String message) {
                super.onFailure(errorCode, message);
                ToastUitl.show(message);
            }
        });
    }

    private void getRooms() {
        RemoteDouyuDataSource.getRooms(getActivity(), id, offset, limit, new HttpCallback<DouyuResult>(getActivity(), DouyuResult.class, true) {

            @Override
            public void onSuccess(DouyuResult douyuResult) {
                super.onSuccess(douyuResult);
                if (douyuResult.isSuccess()) {
                    if (offset == 0) {
                        rooms.clear();
                        rooms.addAll(JsonUtil.parseList(douyuResult.getData(), RoomInfo.class));
                        setAdapter();
                        return;
                    }
                    if (TextUtils.isEmpty(douyuResult.getData())) {
                        ToastUitl.show("没有更多了");
                        roomAdapter.loadMoreEnd();
                    }else {
                        rooms.addAll(JsonUtil.parseList(douyuResult.getData(), RoomInfo.class));
                        setAdapter();
                        roomAdapter.loadMoreComplete();
                    }
                } else {
                    ToastUitl.show(douyuResult.getData());
                }
            }

            @Override
            public void onFailure(int errorCode, String message) {
                super.onFailure(errorCode, message);
            }
        });
    }

    public void setAdapter(){
        if(roomAdapter == null){
            roomAdapter = new BaseQuickAdapter<RoomInfo,BaseViewHolder>(R.layout.item_live,rooms) {

                @Override
                protected void convert(BaseViewHolder helper, RoomInfo item) {
                    helper.setText(R.id.tv_name,item.getNickname());
                    helper.setText(R.id.tv_num,item.getOnline()+"");
                    ImageView iv_room = helper.getView(R.id.iv_room);
                    ImageView iv_head = helper.getView(R.id.iv_head);
                    Glide.with(LiveFragment.this).load(item.getRoom_src()).into(iv_room);
                    Glide.with(LiveFragment.this).load(item.getAvatar_small()).into(iv_head);
                }
            };
            rvRoom.setAdapter(roomAdapter);
            roomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    LiveActivity.launch(getActivity(),rooms.get(position));
                }
            });
            roomAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    offset+=limit;
                    getRooms();
                }
            },rvRoom);

        }else {
            rvRoom.post(new Runnable() {
                @Override
                public void run() {
                    roomAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
