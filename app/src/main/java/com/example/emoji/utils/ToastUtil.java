package com.example.emoji.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.emoji.MyApplication;

/**
 * 创建时间:2020/4/26
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class ToastUtil {

    public static void toastShort(String message){
        Toast.makeText(MyApplication.getInstance(),message,Toast.LENGTH_SHORT).show();
    }
}
