package com.example.emoji.utils.okhttp3;

/**
 * 创建时间:2020/7/25
 * 作者:fr
 * 邮箱:1546352238@qq.com
 *
 *
 *描述：封装回调接口和要转换的实体对象
 */
public class ResponseDataHandle {
    public ResponseCallback mListener = null;
    public Class<?> mClass = null;

    public ResponseDataHandle(ResponseCallback listener){
        this.mListener = listener;
    }

    public ResponseDataHandle(ResponseCallback listener, Class<?> clazz) {
        this.mListener = listener;
        this.mClass = clazz;
    }
}
