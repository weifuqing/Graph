package com.danikula.videocache.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;

/**
 * Created by weifuqing on 2017/8/18 0018.
 */

public class MusicPlayer extends Service{

    private static final MediaPlayer mediaPlayer = new MediaPlayer();
    private static String path = "";

    public static void setPath(String url) {
        path = url;
        start();
    }

    public static MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }

    public static int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public static int getDuration() {
        return mediaPlayer.getDuration();
    }

    public static void setProgress(int progress) {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        mediaPlayer.seekTo(progress);
    }

    public static boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    private static void start() {
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pause(){
        mediaPlayer.pause();
    }

    public static void stop() {
        mediaPlayer.stop();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
