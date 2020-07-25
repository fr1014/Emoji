package com.example.emoji.utils.okhttp3;

/**
 * 创建时间:2020/7/25
 * 作者:fr
 * 邮箱:1546352238@qq.com
 *
 * 描述：请求模式
 */

public class RequestMode {

    public static void getRequest(String url,RequestParams params,ResponseCallback callback,Class<?> calzz){
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url,params),new ResponseDataHandle(callback,calzz));
    }
}
