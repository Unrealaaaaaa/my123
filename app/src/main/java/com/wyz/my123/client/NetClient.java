package com.wyz.my123.client;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetClient {

    private static NetClient netClient;
    public final OkHttpClient client;

    private NetClient(){
        client = initOkHttpClient();
    }

    private OkHttpClient initOkHttpClient(){
        //初始化的时候可以自定义一些参数
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10000, TimeUnit.MILLISECONDS)//设置读取超时为10秒
                .connectTimeout(10000, TimeUnit.MILLISECONDS)//设置链接超时为10秒
                .build();
        return okHttpClient;
    }

    public static NetClient getNetClient(){
        if(netClient == null){
            netClient = new NetClient();
        }
        return netClient;
    }

    public void getRequest(String url, Callback callback){
        final Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = getNetClient().initOkHttpClient().newCall(request);
        call.enqueue(callback);
    }

    public void postRequest(String url, RequestBody requestBody, Callback callback){
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = getNetClient().initOkHttpClient().newCall(request);
        call.enqueue(callback);
    }

    public static String post(String url, RequestBody requestBody) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = getNetClient().initOkHttpClient().newCall(request).execute();
        return response.body().string();
    }
}
