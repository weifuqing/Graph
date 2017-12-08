package com.wfq.graph.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.webkit.ClientCertRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wfq.graph.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weifuqing on 2017/11/24.
 */

public class MyWebView extends WebView{

    private Context mContext;

    public List<String> errorList = new ArrayList<>();


    private final int HANDLER_IMAGE = 1001;
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANDLER_IMAGE://图片识别
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public MyWebView(Context context) {
        this(context,null);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init(){
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);//js交互

        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小


        //缩放操作
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //缓存
        if (AppUtil.isNetworkAvailable(mContext.getApplicationContext())) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }
        //缓存模式如下：
        //LOAD_CACHE_ELSE_NETWORK 优先使用webview中缓存
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        settings.setAllowFileAccess(true); //设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式


        settings.setBlockNetworkImage(false);//解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        setWebChromeClient(new MyWebChromeClient());
        setWebViewClient(new MyWebViewClient());
    }

    private void startIntent(String url){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            if (!errorList.contains(url)) {
                errorList.add(url);
            }
        }
    }

    //作用：处理各种通知 & 请求事件
    public class MyWebViewClient extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            //点击识别二维码
            HitTestResult hit = view.getHitTestResult();
            if(hit!=null){
                int hitType = hit.getType();
                if (hitType == HitTestResult.IMAGE_TYPE
                        || hitType == HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {// 点击图片
                    hit.getExtra();
                    startIntent(url);
                }
            }


            if (url.startsWith("http") && !url.contains(".apk") && !url.endsWith(".pdf")) {
                return false;
            }else {
                if(errorList.contains(url)||url.startsWith("intent")){
                    return true;
                }
                startIntent(url);
                return true;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onLoadResource(WebView view, String url) {//作用：在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
            super.onLoadResource(view, url);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            if(!request.isForMainFrame()){
                loadUrl("file:///android_assets/web/error.html");
                return;
            }
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            loadUrl("file:///android_assets/web/error.html");
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();    //表示等待证书响应
            // handler.cancel();      //表示挂起连接，为默认方式
            // handler.handleMessage(null);    //可做其他处理
        }
    }

    public class MyWebChromeClient extends WebChromeClient{

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }
}
