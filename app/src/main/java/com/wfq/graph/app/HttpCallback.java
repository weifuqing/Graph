package com.wfq.graph.app;

import android.app.Activity;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;
import com.wfq.graph.R;
import com.wfq.graph.utils.ToastUitl;
import com.wfq.graph.widget.dialog.LoadingDialog;

/**
 * Created by weifuqing on 2017/7/21 0021.
 */

public abstract class  HttpCallback<T> {

    private Activity activity;
    private LoadingDialog loadingDialog;
    private AVLoadingIndicatorView avi;
    View dialogView;

    private Class clazz;
    public <T> HttpCallback(Activity activity,Class<T> clazz,boolean isShow){
        this.clazz = clazz;
        if(isShow&&activity!=null){
            loadingDialog = new LoadingDialog(activity);
            dialogView = View.inflate(activity, R.layout.dialog_loading,null);
            avi = (AVLoadingIndicatorView) dialogView.findViewById(R.id.avi);
            this.activity = activity;
            startAnim();
        }
    }

    public void startAnim(){
        if(loadingDialog!=null){
            loadingDialog.show();
        }
        if(avi!=null){
            avi.show();
        }
    }

    public void stopAnim(){
        if(loadingDialog!=null&&loadingDialog.isShowing()&&activity!=null&&!activity.isFinishing()){
            loadingDialog.dismiss();
        }
        if(avi!=null){
            avi.hide();
        }
    }

    public Class<T> getT_class(){
        return clazz;
    }

    public void onSuccess(T t){
        stopAnim();
    }

    public void onFailure(int errorCode, String message){
        stopAnim();
        ToastUitl.show(message);
    }
}
