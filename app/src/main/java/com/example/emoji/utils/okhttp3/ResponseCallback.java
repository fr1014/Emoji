package com.example.emoji.utils.okhttp3;

/**
 * 创建时间:2020/7/25
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public interface ResponseCallback {
    //请求成功回调事件
    void onSuccess(Object responseObj);

    //请求失败回调事件处理
    void onFailure(OkHttpException exception);
}
