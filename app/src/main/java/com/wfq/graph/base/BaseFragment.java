package com.wfq.graph.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sina.weibo.sdk.api.share.Base;
import com.wfq.graph.utils.ConstantUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by weifuqing on 2017/6/14 0014.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    String TAG = "";
    private Unbinder unbinder;

    public View fragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentView = inflater.inflate(getLayoutId(),null);
        unbinder = ButterKnife.bind(this,fragmentView);
        initView();
        initListener();
        initData();
        return fragmentView;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    @Override
    public void onClick(View view) {

    }

    protected void gotoActivity(Class<? extends Activity> clazz, Bundle bundle){
        Intent intent = new Intent(getActivity(),clazz);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null){
            unbinder.unbind();
        }
    }

}
