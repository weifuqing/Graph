package com.wfq.graph.ui.main;

import android.os.PowerManager;
import android.view.View;
import android.widget.TextView;

import com.danikula.videocache.service.MusicPlayer;
import com.danikula.videocache.widget.MusicSeekBar;
import com.danikula.videocache.widget.VideoCacheView;
import com.example.statistics.StatService;
import com.wfq.graph.R;
import com.wfq.graph.app.MyApplication;
import com.wfq.graph.base.BaseLazyFragment;
import com.wfq.graph.utils.ConstantUtil;

import butterknife.BindView;

/**
 * Created by weifuqing on 2017/6/8 0008.
 */

public class MsgFragment extends BaseLazyFragment {


    @BindView(R.id.videoCacheView)
    VideoCacheView videoCacheView;

    @BindView(R.id.musicSeekBar)
    MusicSeekBar musicSeekBar;

    @BindView(R.id.tv_start)
    TextView tv_start;

    @BindView(R.id.tv_pause)
    TextView tv_pause;

    @BindView(R.id.tv_next)
    TextView tv_next;

    @Override
    public void fetchData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_msg;
    }

    @Override
    protected void initView() {
//        videoCacheView.init("http://audiofile.ifcert.org.cn/aac00001");
//        videoCacheView.init("http://audiofile.ifcert.org.cn/00001.mp3");
        if(ConstantUtil.urls.isEmpty()) {
            ConstantUtil.urls.add("http://audiofile.ifcert.org.cn/00001.mp3");
            ConstantUtil.urls.add("http://audiofile.ifcert.org.cn/00002.mp3");
            ConstantUtil.urls.add("http://audiofile.ifcert.org.cn/00003.mp3");
            ConstantUtil.urls.add("http://audiofile.ifcert.org.cn/00004.mp3");
            ConstantUtil.urls.add("http://audiofile.ifcert.org.cn/00005.mp3");
            ConstantUtil.urls.add("http://audiofile.ifcert.org.cn/00006.mp3");
        }
        musicSeekBar.init(ConstantUtil.urls.get(ConstantUtil.currentPosition));
    }

    @Override
    protected void initListener() {
        tv_next.setOnClickListener(this);
        tv_pause.setOnClickListener(this);
        tv_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.tv_start:
                MusicPlayer.getMediaPlayer().start();
                break;
            case R.id.tv_next:
                if(ConstantUtil.currentPosition==ConstantUtil.urls.size()-1){
                    ConstantUtil.currentPosition = 0;
                }else {
                    ConstantUtil.currentPosition+=1;
                }
                MusicPlayer.getMediaPlayer().reset();
                musicSeekBar.setProgress(0);
                musicSeekBar.setSecondaryProgress(0);
                musicSeekBar.init(ConstantUtil.urls.get(ConstantUtil.currentPosition));
                break;
            case R.id.tv_pause:
                MusicPlayer.getMediaPlayer().pause();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        videoCacheView.start();
        musicSeekBar.start();
        StatService.startPage("信息");
    }

    @Override
    public void onPause() {
        super.onPause();
//        videoCacheView.stop();
        musicSeekBar.stop();
        StatService.endPage("信息");
    }

    @Override
    public void onDestroyView() {
//        videoCacheView.onDestory();
        super.onDestroyView();
    }

}
