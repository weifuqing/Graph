package com.wfq.graph.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wfq.graph.R;
import com.wfq.graph.base.BaseFragment;
import com.wfq.graph.data.bean.Score;
import com.wfq.graph.widget.BrokenLine;
import com.wfq.graph.widget.HomeArc;
import com.wfq.graph.widget.HomeColumnar;
import com.wfq.graph.widget.HomeDiagram;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by weifuqing on 2017/6/8 0008.
 */

public class DiscoveryFragment extends BaseFragment {

    @BindView(R.id.broken_line)
    BrokenLine brokenLine;

    private List<Integer> brokenList = new ArrayList<>();
    private List<Integer> verticalList = new ArrayList<>();
    private List<String> horizontalList = new ArrayList<>();
    private String vertical_unit = "万元";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discovery;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        for (int i=0;i<7;i++){
            brokenList.add(getRandom(0,50));
            horizontalList.add("02-0"+(i+1));
        }
        for (int j=0;j<=50;j+=10){
            verticalList.add(j);
        }
        brokenLine.init(brokenList,verticalList,horizontalList,vertical_unit);
    }

    public int getRandom(int min,int max){
        return (int) Math.round(Math.random()*(max-min)+min);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
