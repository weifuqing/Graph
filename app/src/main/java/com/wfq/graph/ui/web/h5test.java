package com.wfq.graph.ui.web;

import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.wfq.graph.R;
import com.wfq.graph.base.BaseActivity;

/**
 * Created by Admin on 2017/3/23.
 */
public class h5test extends BaseActivity {

    WebView web;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_h5test;
    }

    @Override
    protected void initView() {
        web = (WebView) findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);

        web.loadUrl("file:///android_asset/device_id.html");
//        web.loadUrl("http://192.168.60.103:8080/financial-web-m/jsp/intelligence/attention.jsp");

        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//return super.onJsAlert(view, url, message, result);
                Toast.makeText(h5test.this, message, Toast.LENGTH_LONG).show();
                Log.e("weifuqing",message);
                return true;
            }});
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

}
