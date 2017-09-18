package com.wfq.graph.base;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by weifuqing on 2017/7/13 0013.
 */

public class RecyclerViewHolder<T> extends BaseViewHolder<T> {


    public RecyclerViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
    }
}
