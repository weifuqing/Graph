package com.wfq.graph.app;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;
import com.wfq.graph.R;
import com.wfq.graph.data.bean.ArrayResult;
import com.wfq.graph.data.bean.Result;
import com.wfq.graph.utils.ToastUitl;
import com.wfq.graph.widget.dialog.LoadingDialog;

import java.util.ArrayList;

/**
 * Created by weifuqing on 2017/7/21 0021.
 */

public abstract class  HttpCallback<T> {

    private Activity activity;
    private LoadingDialog loadingDialog;
    private AVLoadingIndicatorView avi;
    View dialogView;

    private Class clazz;
    private boolean isList;

    public <T> HttpCallback(Activity activity,Class<T> clazz,boolean isShow,boolean isList){
        this.clazz = clazz;
        this.isList = isList;
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

    public boolean isList(){
        return isList;
    }

    public void onSuccess(T t){
        stopAnim();
    }

    public void onSuccess(ArrayList<T> list){
        stopAnim();
    }

    public void onError(Result result){
        stopAnim();
        ToastUitl.show("访问失败："+result.getError());
    }

    public void onError(ArrayResult arrayResult){
        stopAnim();
        ToastUitl.show("访问失败："+arrayResult.getError());
    }

    public void onFailure(int errorCode, String message){
        stopAnim();
        ToastUitl.show(message);
    }
}
