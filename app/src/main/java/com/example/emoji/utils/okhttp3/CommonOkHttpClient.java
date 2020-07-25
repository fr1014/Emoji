package com.example.emoji.utils.okhttp3;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 创建时间:2020/7/25
 * 作者:fr
 * 邮箱:1546352238@qq.com
 *
 * 描述: OKHttpClient对象
 */
public class CommonOkHttpClient {
    //超时时间
    private static final int TIME_OUT = 20;
    private static OkHttpClient mOkHttpClient;

    /**
     * 为Client配置参数，使用静态语句块来配置
     * 只执行一次，运行一开始就开辟了内存，内存放在全局
     */
    static {
        //
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder
                //为构建者设置超时时间
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT,TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT,TimeUnit.SECONDS)
                ////webSocket轮训间隔(单位:秒)
                .pingInterval(20, TimeUnit.SECONDS)
                //添加https支持
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                })
                .sslSocketFactory(HttpsUtils.initSSLSocketFactory(), HttpsUtils.initTrustManager());

        mOkHttpClient = okHttpBuilder.build();
    }

    public static Call sendRequest(Request request,CommonJsonCallback callback){
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
        return call;
    }

    //GET请求
    public static Call get(Request request,ResponseDataHandle handle){
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }
}
