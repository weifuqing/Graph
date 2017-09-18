package com.wfq.graph.ui.main;

import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.wfq.graph.R;
import com.wfq.graph.base.BaseActivity;
import com.wfq.graph.base.ToolbarActivity;
import com.wfq.graph.service.LockService;

import am.widget.gradienttabstrip.GradientTabStrip;
import butterknife.BindView;

/**
 * Created by weifuqing on 2017/4/10.
 */
public class MainActivity extends ToolbarActivity {


    @BindView(R.id.vp_main)
    ViewPager vp_main;
    @BindView(R.id.tab_bottom)
    GradientTabStrip tab_bottom;


    MainPagerAdapter pagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        vp_main.setAdapter(pagerAdapter);
        tab_bottom.setAdapter(pagerAdapter);
        tab_bottom.bindViewPager(vp_main);
        setTitle(pagerAdapter.getPageTitle(vp_main.getCurrentItem()));

        startService(new Intent(this, LockService.class));

    }

    @Override
    protected void initListener() {
        vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(pagerAdapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {

    }
}
