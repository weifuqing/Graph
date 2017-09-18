package com.wfq.graph.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by dell on 2016/12/16.
 */
public abstract class BasicAdapter<T> extends BaseAdapter {

    List<T> list;
    Context mContext;
    int layoutId;

    public BasicAdapter(Context context, List<T> list, int layoutId){
        this.list = list;
        mContext = context;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = View.inflate(mContext, layoutId,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        onBindHolder(holder,list.get(i),i);
        return view;
    }

    public abstract void onBindHolder(ViewHolder holder,T item,int position);
}
