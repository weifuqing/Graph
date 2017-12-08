package com.wfq.graph.ui.live;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.wfq.graph.R;
import com.wfq.graph.app.BaseRequest;
import com.wfq.graph.app.HttpCallback;
import com.wfq.graph.base.BaseActivity;
import com.wfq.graph.data.bean.DouyuResult;
import com.wfq.graph.data.bean.douyu.RoomInfo;
import com.wfq.graph.data.source.remote.RemoteDouyuDataSource;
import com.wfq.graph.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by weifuqing on 2017/11/3.
 */

public class LiveActivity extends BaseActivity {


    @BindView(R.id.videoView)
    VideoView mVideoView;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_speed)
    TextView tv_speed;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_head)
    ImageView iv_head;

    MediaController mediaController;

    private static String ROOMINFO = "ROOMINFO";

    private RoomInfo roomInfo;
    private String liveUrl = "";

    public static void launch(Context context, RoomInfo roomInfo) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ROOMINFO, roomInfo);
        Intent intent = new Intent(context, LiveActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_live;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        roomInfo = bundle.getParcelable(ROOMINFO);
    }

    @Override
    protected void initListener() {
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setPlaybackSpeed(1.0f);
            }
        });
    }

    @Override
    protected void initData() {
        RemoteDouyuDataSource.getLiveUrl(getActivity(), roomInfo.getRoom_id(), new HttpCallback<DouyuResult>(getActivity(), DouyuResult.class, true) {

            @Override
            public void onSuccess(DouyuResult douyuResult) {
                super.onSuccess(douyuResult);
                if (douyuResult.isSuccess()) {
                    try {
                        JSONObject jsonObject = new JSONObject(douyuResult.getData());
                        liveUrl = jsonObject.optString("hls_url");
                        play();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ToastUitl.show("播放地址解析异常");
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

    private void play() {
        mVideoView.setVideoPath(liveUrl);
        mediaController = new MediaController(this);
        mVideoView.setMediaController(mediaController);
        mVideoView.requestFocus();
    }
}
