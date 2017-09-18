package com.wfq.graph.ui.live;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.statistics.StatService;
import com.wfq.graph.R;
import com.wfq.graph.base.BaseActivity;
import com.wfq.graph.data.bean.douyu.RoomInfo;
import com.wfq.graph.utils.ConstantUtil;

import butterknife.BindView;

/**
 * Created by weifuqing on 2017/7/17 0017.
 */

public class LiveWebActivity extends BaseActivity {

    @BindView(R.id.web_live)
    WebView web_live;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private RoomInfo roomInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_web;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        roomInfo = bundle.getParcelable(ConstantUtil.ROOM_DOUYU);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        tv_title.setText(roomInfo.getRoom_name());

        //webview设置
        WebSettings webSettings = web_live.getSettings();
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放
    }

    @Override
    protected void initListener() {
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        web_live.setWebViewClient(new MyWebViewClient());
        web_live.setWebChromeClient(new MyWebChromeClient());
    }

    @Override
    protected void initData() {
        web_live.loadUrl(roomInfo.getUrl());
    }

    @Override
    protected void onResume() {
        super.onResume();
        web_live.onResume();
        StatService.startPage(roomInfo.getRoom_name());
    }

    @Override
    protected void onPause() {
        super.onPause();
        web_live.onPause();
        StatService.endPage(roomInfo.getRoom_name());
    }

    public class MyWebChromeClient extends WebChromeClient{

    }

    public class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.startsWith("http")&&!url.contains(".apk")){
                view.loadUrl(url);
                return true;
            }else {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }

                return false;
            }
        }
    }
}
