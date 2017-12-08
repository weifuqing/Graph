package com.wfq.graph.ui.lock;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wfq.graph.R;
import com.wfq.graph.base.BaseActivity;
import com.wfq.graph.utils.ConstantUtil;
import com.wfq.graph.widget.swipe_back.SwipeBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by weifuqing on 2017/8/23 0023.
 */

public class LockActivity extends SwipeBackActivity implements View.OnClickListener {


    @BindView(R.id.lock_music_name)
    TextView tv_name;
    @BindView(R.id.lock_music_pre)
    ImageView tv_pre;
    @BindView(R.id.lock_music_play)
    ImageView tv_play;
    @BindView(R.id.lock_music_next)
    ImageView tv_next;

    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
                        // bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);


        setContentView(R.layout.activity_lock);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        setName();
        tv_next.setOnClickListener(this);
        tv_play.setOnClickListener(this);
        tv_pre.setOnClickListener(this);
    }

    private void setName(){
        tv_name.setText(ConstantUtil.urls.get(ConstantUtil.currentPosition));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lock_music_play:
                break;
            case R.id.lock_music_pre:
                if(ConstantUtil.currentPosition==0){
                    ConstantUtil.currentPosition = ConstantUtil.urls.size()-1;
                }else {
                    ConstantUtil.currentPosition--;
                }
                setName();
                break;
            case R.id.lock_music_next:
                if(ConstantUtil.currentPosition==ConstantUtil.urls.size()-1){
                    ConstantUtil.currentPosition = 0;
                }else {
                    ConstantUtil.currentPosition++;
                }
                setName();
                break;
        }
    }
}
