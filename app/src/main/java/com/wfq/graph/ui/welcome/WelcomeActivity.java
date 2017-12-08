package com.wfq.graph.ui.welcome;

import android.text.TextUtils;
import android.widget.TextView;

import com.github.irvingryan.VerifyCodeView;
import com.wfq.graph.R;
import com.wfq.graph.base.BaseActivity;
import com.wfq.graph.ui.main.DiagramActivity;
import com.wfq.graph.ui.main.MainActivity;
import com.wfq.graph.ui.web.h5test;
import com.wfq.graph.utils.AppUtil;
import com.wfq.graph.utils.ConstantUtil;
import com.wfq.graph.utils.KeyBoardUtil;
import com.wfq.graph.utils.MD5Util;
import com.wfq.graph.utils.SPUtil;
import com.wfq.graph.utils.ToastUitl;

import butterknife.BindView;

/**
 * Created by Admin on 2017/3/31.
 */
public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.codeView)
    VerifyCodeView codeView;
    @BindView(R.id.tv_tip)
    TextView tv_tip;

    String passWord;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {

        passWord = SPUtil.getString(this, ConstantUtil.PASSWORD_MD5);

        if (TextUtils.isEmpty(passWord)) {
            tv_tip.setText("请先设置密码");
        } else {
            tv_tip.setText("请输入密码验证身份");
        }
        codeView.postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyBoardUtil.openKeybord(codeView);
            }
        },100);
    }


    @Override
    protected void initListener() {
        codeView.setListener(new VerifyCodeView.OnTextChangListener() {
            @Override
            public void afterTextChanged(String text) {
                if (text.length() > 5) {
                    verify(text);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }


    private void verify(String input) {
        if (TextUtils.isEmpty(passWord)) {
            SPUtil.saveString(this,ConstantUtil.PASSWORD_MD5,MD5Util.encode(input));
            gotoActivity(MainActivity.class);
            finish();
        } else if (MD5Util.encode(input).equals(passWord)) {
            gotoActivity(MainActivity.class);
            finish();
        }else {
            ToastUitl.show("输入密码错误");
            codeView.setText("");
        }
    }
}
