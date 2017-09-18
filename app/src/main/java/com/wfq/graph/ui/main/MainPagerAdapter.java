package com.wfq.graph.ui.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;

import com.wfq.graph.R;
import com.wfq.graph.base.BaseFragment;
import com.wfq.graph.base.BaseLazyFragment;

import java.util.Arrays;
import java.util.List;

import am.widget.gradienttabstrip.GradientTabStrip;

/**
 * Created by weifuqing on 2017/5/27 0027.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter implements GradientTabStrip.GradientTabAdapter {


    private List<String> titles = Arrays.asList("信息", "联系人", "神奇", "发现", "直播");
    private List<BaseFragment> fragments = Arrays.asList(new MsgFragment(),new ContactFragment(),
            new MagicFragment(),new DiscoveryFragment(),new LiveFragment());

    private int[] normalIds = {
            R.mipmap.msg_normal,
            R.mipmap.contact_normal,
            R.mipmap.magic_normal,
            R.mipmap.discovery_normal,
            R.mipmap.live_normal
    };

    private int[] selectIds = {
            R.mipmap.msg_select,
            R.mipmap.contact_select,
            R.mipmap.magic_select,
            R.mipmap.discovery_select,
            R.mipmap.live_select
    };

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Drawable getNormalDrawable(int position, Context context) {
        return ContextCompat.getDrawable(context,normalIds[position]);
    }

    @Override
    public Drawable getSelectedDrawable(int position, Context context) {
        return ContextCompat.getDrawable(context,selectIds[position]);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public boolean isTagEnable(int position) {
        return false;
    }

    @Override
    public String getTag(int position) {
        return "";
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }
}
