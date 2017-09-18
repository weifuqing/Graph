package com.wfq.graph.base;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

/**
 * Created by weifuqing on 2017/7/13 0013.
 */

public class RecyclerAdapter<T> extends RecyclerArrayAdapter<T> {


    public RecyclerAdapter(Context context, List<T> objects) {
        super(context, objects);
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
}
