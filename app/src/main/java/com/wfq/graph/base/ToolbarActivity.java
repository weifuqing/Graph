package com.wfq.graph.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wfq.graph.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public  abstract class ToolbarActivity extends BaseActivity{


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;

    protected abstract int getLayoutId();

    protected  void initView(){

        if(toolbar!=null){
            setSupportActionBar(toolbar);
        }
        initViews();
    };

    protected abstract void initViews();

    protected abstract void initListener();

    protected abstract void initData();

    protected <T extends View> T $(int resId) {
        return (T) findViewById(resId);
    }


    @Override
    public void setTitle(CharSequence title){
        if(toolbar!=null){
            toolbar.setTitle("");
        }
        if(tv_title!=null){
            tv_title.setText(title);
        }
    }

    public void initBack(){
        if(toolbar!=null){
            toolbar.setNavigationIcon(R.mipmap.ic_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 跳转Activity
     *
     * @param activity
     * @param bundle
     */
    protected void gotoActivity(Class<? extends Activity> activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转Activity
     *
     * @param activity
     */
    protected void gotoActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);

    }

    /**
     * 跳转activity for result
     *
     * @param activity
     * @param bundle
     * @param requestCode
     */
    protected void gotoActivityForResult(Class<? extends Activity> activity, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

}
