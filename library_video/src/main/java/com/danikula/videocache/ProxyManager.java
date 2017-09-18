package com.danikula.videocache;

import android.content.Context;

import java.io.File;

/**
 * Created by weifuqing on 2017/8/18 0018.
 */

public class ProxyManager {

    private static HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {

        return proxy == null ? (proxy = newProxy(context)) :proxy;
    }

    private static synchronized HttpProxyCacheServer newProxy(Context context) {
        return new HttpProxyCacheServer.Builder(context.getApplicationContext())
                .cacheDirectory(new File(context.getExternalCacheDir(), "video-cache"))
                .build();
    }
}
