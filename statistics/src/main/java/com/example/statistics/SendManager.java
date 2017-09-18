package com.example.statistics;

import android.widget.Toast;

import com.example.statistics.bean.BaseResult;
import com.example.statistics.utils.LogUtil;

/**
 * Created by weifuqing on 2017/8/31 0031.
 */

public class SendManager {

    public static void sendEvents(final String data){
        BaseRequest.requestJson(StatConfig.getContext(), HttpUrl.addEvents, "["+data+"]", new HttpCallback<BaseResult>(BaseResult.class,false) {

            @Override
            public void onSuccess(BaseResult baseResult) {
                super.onSuccess(baseResult);
                if(baseResult.getCode()!=200){
                    LogUtil.saveLog(data);
                }else {
                    Toast.makeText(StatConfig.getContext(), "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int errorCode, String message) {
                super.onFailure(errorCode, message);
                LogUtil.saveLog(data);
            }
        });
    }
}
