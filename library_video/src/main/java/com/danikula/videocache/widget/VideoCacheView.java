package com.danikula.videocache.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.ProxyManager;
import com.danikula.videocache.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.SeekBarTouchStop;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

import java.io.File;

/**
 * Created by weifuqing on 2017/8/18 0018.
 */

public class VideoCacheView extends RelativeLayout implements CacheListener,OnViewChangedListener, HasViews {

    private Context mContext;
    private VideoView videoView;
    private SeekBar progressBar;

    private String url;

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private final VideoProgressUpdater updater = new VideoProgressUpdater();

    public VideoCacheView(Context context) {
        this(context,null);
    }

    public VideoCacheView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VideoCacheView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View view = View.inflate(context, R.layout.view_video ,this);
        videoView = (VideoView) view.findViewById(R.id.videoView);
        progressBar = (SeekBar) view.findViewById(R.id.progressBar);
    }

    public void init(String url){
        this.url = url;
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    //检查缓存
    private void checkCachedState() {
        HttpProxyCacheServer proxy = ProxyManager.getProxy(mContext);
        boolean fullyCached = proxy.isCached(url);
        if (fullyCached) {
            progressBar.setSecondaryProgress(100);
        }
    }

    public void start(){
        updater.start();
    }

    public void stop(){
        updater.stop();
    }

    public void onDestory(){
        videoView.stopPlayback();
        ProxyManager.getProxy(mContext).unregisterCacheListener(this);
    }

    //开始播放
    private void startVideo() {
        HttpProxyCacheServer proxy = ProxyManager.getProxy(mContext);
        proxy.registerCacheListener(this, url);
        String proxyUrl = proxy.getProxyUrl(url);
        videoView.setVideoPath(proxyUrl);
        videoView.start();
    }

    void seekVideo() {
        int videoPosition = videoView.getDuration() * progressBar.getProgress() / 100;
        videoView.seekTo(videoPosition);
    }

    private void updateVideoProgress() {
        int videoProgress = videoView.getCurrentPosition() * 100 / videoView.getDuration();
        progressBar.setProgress(videoProgress);
    }

    @Override
    public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
        progressBar.setSecondaryProgress(percentsAvailable);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        if(progressBar!=null){
            progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    seekVideo();
                }
            });
        }
        afterViewInjected();
    }

    @AfterViews
    void afterViewInjected() {
        checkCachedState();
        startVideo();
    }


    private final class VideoProgressUpdater extends Handler {

        public void start() {
            sendEmptyMessage(0);
        }

        public void stop() {
            removeMessages(0);
        }

        @Override
        public void handleMessage(Message msg) {
            updateVideoProgress();
            sendEmptyMessageDelayed(0, 500);
        }
    }
}
