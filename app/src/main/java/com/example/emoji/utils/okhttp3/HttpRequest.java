package com.example.emoji.utils.okhttp3;

import com.example.emoji.data.entity.CategoryPhoto;

/**
 * 创建时间:2020/7/25
 * 作者:fr
 * 邮箱:1546352238@qq.com
 * <p>
 * 封装：所有的请求接口
 */
public class HttpRequest {
//https://pixabay.com/api/?key=12655070-2a387b187e8c4744f56c20015&q=%E7%BE%8E%E5%A5%B3&image_type=photo&lang=zh

    public static void getCategory(RequestParams params, ResponseCallback callback) {
        RequestMode.getRequest("", params, callback, CategoryPhoto.class);
    }
}
