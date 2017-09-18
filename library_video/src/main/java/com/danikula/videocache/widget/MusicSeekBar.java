package com.danikula.videocache.widget;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.ProxyManager;
import com.danikula.videocache.service.MusicPlayer;

import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

import java.io.File;

/**
 * Created by weifuqing on 2017/8/18 0018.
 */

public class MusicSeekBar extends SeekBar implements CacheListener {

    private Context mContext;
    private String url;

    private final OnViewChangedNotifier onViewChangedNotifier= new
            OnViewChangedNotifier();

    private final VideoProgressUpdater updater = new VideoProgressUpdater();

    public MusicSeekBar(Context context) {
        this(context,null);
    }

    public MusicSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MusicSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
    }

    public void init(String url){
        this.url = url;
        setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateMusicProgress();
            }
        });
        checkCachedState();
        startVideo();
    }

    //seekbar进度控制
    public void start(){
        updater.start();
    }
    public void stop(){
        updater.stop();
    }


    //检查缓存
    private void checkCachedState() {
        HttpProxyCacheServer proxy = ProxyManager.getProxy(mContext);
        boolean fullyCached = proxy.isCached(url);
        if (fullyCached) {
            setSecondaryProgress(100);
        }
    }

    //开始播放缓存
    private void startVideo() {
        HttpProxyCacheServer proxy = ProxyManager.getProxy(mContext);
        proxy.registerCacheListener(this, url);
        String proxyUrl = proxy.getProxyUrl(url);
        MusicPlayer.setPath(proxyUrl);
    }

    //更新SeekBar进度
    private void updateBarProgress(){
        int videoProgress = MusicPlayer.getCurrentPosition() * getMax() / MusicPlayer.getDuration();
        setProgress(videoProgress);
    }

    //更新音乐进度
    private void updateMusicProgress(){
        int videoPosition = MusicPlayer.getDuration() * getProgress() / getMax();
        MusicPlayer.setProgress(videoPosition);
    }

    @Override
    public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
        setSecondaryProgress(percentsAvailable*getMax()/100);
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
            updateBarProgress();
            sendEmptyMessageDelayed(0, 500);
        }
    }
}
