package com.wfq.graph.ui.main;

import android.os.PowerManager;
import android.view.View;
import android.widget.TextView;

import com.wfq.graph.R;
import com.wfq.graph.app.MyApplication;
import com.wfq.graph.base.BaseLazyFragment;
import com.wfq.graph.utils.ConstantUtil;

import butterknife.BindView;

/**
 * Created by weifuqing on 2017/6/8 0008.
 */

public class MsgFragment extends BaseLazyFragment {



    @Override
    public void fetchData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_msg;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){

        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
