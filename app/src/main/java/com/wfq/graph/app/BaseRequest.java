package com.wfq.graph.app;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qiniu.android.utils.StringUtils;
import com.wfq.graph.data.bean.ArrayResult;
import com.wfq.graph.data.bean.Result;
import com.wfq.graph.data.bean.douyu.RoomInfo;
import com.wfq.graph.utils.AppUtil;
import com.wfq.graph.utils.GsonUtil;
import com.wfq.graph.utils.ToastUitl;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by weifuqing on 2017/7/19 0019.
 */

public class BaseRequest {


    private static final int CODE_NETWORK_ERROR = -1;
    private static final int CODE_CALL_FAIL = -2;
    private static final int CODE_RESPONSE_ERROR = -3;

    private static final String ERROR_CALL = "网络连接失败";
    private static final String ERROR_NETWORK = "请检查网络设置";
    private static final String ERROR_RESPONSE = "服务器处理异常";

    private static final int MODE_GET = 1000;
    private static final int MODE_POST = 1001;
    private static final int MODE_PUT = 1002;
    private static final int MODE_DELETE = 1003;

    public static final MediaType mediaTypeJSON =
            MediaType.parse("application/json; charset=utf-8");
    public static final MediaType mediaTypeFile =
            MediaType.parse("octet-stream");


    private static int TIMEOUT_READ = 30;
    private static int TIMEOUT_WRITE = 30;
    private static int TIMEOUT_CONNECT = 10;

    private static OkHttpClient okHttpClient;
    private static Handler handler = new Handler(Looper.getMainLooper());

    private static OkHttpClient getOkHttpClient() {
        synchronized (BaseRequest.class) {
            if (okHttpClient == null) {
                okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
                        .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                        .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
                        .hostnameVerifier(new HostnameVerifier() {
                            @Override
                            public boolean verify(String s, SSLSession sslSession) {
                                return true;
                            }
                        })
                        .build();
            }
        }
        return okHttpClient;
    }

    //访问失败
    private static void sendFail(final HttpCallback callback, final int error_code, final String error_message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(error_code, error_message);
            }
        });
    }

    private static void sendSuccess(final HttpCallback callback, final String s) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(GsonUtil.parseObject(s,callback.getT_class()));
//                    if (callback.isList()) {
//                        ArrayResult arrayResult = GsonUtil.parseObject(s, ArrayResult.class);
//                        if (arrayResult.getError() == 200) {
//                            callback.onSuccess(arrayResult.getData());
//                        } else {
//                            callback.onError(arrayResult);
//                        }
//                    } else {
//                        Result result = GsonUtil.parseObject(s, Result.class);
//                        if (result.getError() == 200) {
//                            callback.onSuccess(result.getData());
//                        } else {
//                            callback.onError(result);
//                        }
//                    }
            }
        });
    }


    private static void requestAsync(Context context, String url, Map<String, String> stringMap, Map<String, File> fileMap, String json, int mode, final HttpCallback callback) {
        if (!AppUtil.isNetworkAvailable(context)) {
            sendFail(callback, CODE_NETWORK_ERROR, ERROR_NETWORK);
            return;
        }

        //非get方法请求表单
        RequestBody requestBody = null;
        if (mode != MODE_GET && TextUtils.isEmpty(json)) {
            FormBody.Builder formBuilder = new FormBody.Builder();
            if (stringMap != null) {
                for (String key : stringMap.keySet()) {
                    formBuilder.add(key, TextUtils.isEmpty(stringMap.get(key)) ? "" : stringMap.get(key));
                }
            }
            requestBody = formBuilder.build();

            if (fileMap != null && !fileMap.isEmpty()) {
                MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
                multiBuilder.addPart(requestBody);
                for (String key : fileMap.keySet()) {
                    File file = fileMap.get(key);
                    if (file != null) {
                        multiBuilder.addFormDataPart(key, file.getName(), RequestBody.create(mediaTypeFile, file));
                    }
                }
                requestBody = multiBuilder.build();
            }
        } else if (!TextUtils.isEmpty(json)) {
            requestBody = RequestBody.create(mediaTypeJSON, json);
        }

        //请求创建
        Request request = null;

        switch (mode) {
            case MODE_PUT:
                request = new Request.Builder()
                        .tag(context)
                        .url(url)
                        .put(requestBody)
                        .build();
                break;
            case MODE_GET:
                request = new Request.Builder()
                        .tag(context)
                        .url(url + getParameters(stringMap))
                        .get()
                        .build();
                break;
            case MODE_DELETE:
                request = new Request.Builder()
                        .tag(context)
                        .url(url)
                        .delete(requestBody)
                        .build();
                break;
            case MODE_POST:
                request = new Request.Builder()
                        .tag(context)
                        .url(url)
                        .post(requestBody)
                        .build();
                break;
        }

        final Call call = getOkHttpClient().newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFail(callback, CODE_CALL_FAIL, ERROR_CALL);
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (response == null) {
                    sendFail(callback, CODE_RESPONSE_ERROR, ERROR_RESPONSE);
                } else if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        sendSuccess(callback,result);
                    }catch (Exception e){
                        sendFail(callback, response.code(),ERROR_RESPONSE);
                    }
                } else {
                    try {
                        sendFail(callback, response.code(), TextUtils.isEmpty(ERROR_RESPONSE) ? response.body().string() : ERROR_RESPONSE);
                    }catch (Exception e){
                        sendFail(callback, response.code(),ERROR_RESPONSE);
                    }

                }
            }
        });
    }

    public static void requestJson(Context context, String url, String json, HttpCallback callback) {
        requestAsync(context, url, null, null, json, MODE_POST, callback);
    }

    public static void requestPost(Context context, String url, Map<String, String> stringMap, Map<String, File> fileMap, HttpCallback callback) {
        requestAsync(context, url, stringMap, fileMap, null, MODE_POST, callback);
    }

    public static void requestGet(Context context, String url, Map<String, String> stringMap, HttpCallback callback) {
        requestAsync(context, url, stringMap, null, null, MODE_GET, callback);
    }

    public static void requestDelete(Context context, String url, Map<String, String> stringMap, HttpCallback callback) {
        requestAsync(context, url, stringMap, null, null, MODE_DELETE, callback);
    }

    public static void requestPut(Context context, String url, Map<String, String> stringMap, HttpCallback callback) {
        requestAsync(context, url, stringMap, null, null, MODE_PUT, callback);
    }


    public static String getParameters(Map<String, String> map) {
        Iterator entries = map.entrySet().iterator();
        String parameter = "";
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) entries.next();
            if (!StringUtils.isNullOrEmpty(entry.getValue())) {
                if (!TextUtils.isEmpty(parameter)) {
                    parameter += "&";
                } else {
                    parameter += "?";
                }
                parameter += entry.getKey() + "=" + URLEncoder.encode(entry.getValue());
            }
        }
        return parameter;
    }
}
