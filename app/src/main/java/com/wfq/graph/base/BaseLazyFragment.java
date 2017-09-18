package com.wfq.graph.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by dell on 2016/7/14.
 */
public abstract class BaseLazyFragment extends BaseFragment {




    protected boolean isViewInitialized;
    protected boolean isVisibleToUser;
    protected boolean isDataInitialized;

    public BaseLazyFragment() {

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitialized = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public boolean prepareFetchData(){
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate){
        if(isViewInitialized&&isVisibleToUser&&(!isDataInitialized||forceUpdate)){
            fetchData();
            isDataInitialized = true;
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void initData() {
        return;
    }

    //加载数据
    public abstract void fetchData();

}
