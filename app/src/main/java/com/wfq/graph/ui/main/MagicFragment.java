package com.wfq.graph.ui.main;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.wfq.graph.R;
import com.wfq.graph.base.BaseLazyFragment;
import com.wfq.graph.ui.web.h5test;
import com.wfq.graph.utils.ToastUitl;

import butterknife.BindView;

/**
 * Created by weifuqing on 2017/6/8 0008.
 */

public class MagicFragment extends BaseLazyFragment {


    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.tv_open)
    TextView tv_open;

    @Override
    public void fetchData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_h5test;
    }

    @Override
    protected void initView() {
        web.getSettings().setJavaScriptEnabled(true);
//        web.loadUrl("file:///android_asset/web/error.html");
//        web.addJavascriptInterface(this,"android");
        web.loadUrl("http://app.ifcert.org.cn/share/notice/report1/Index-1711-23-1.jsp");

    }

    @JavascriptInterface
    public void hellojs(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUitl.show("hello js");
            }
        });

    }

    @Override
    protected void initListener() {

        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                ToastUitl.show(message);
                return true;
            }});
//        tv_open.setOnClickListener(this);
        tv_open.setText("打开理财安全助手");
        tv_open.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_open:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("financial://app"));
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void initData() {

    }
}
