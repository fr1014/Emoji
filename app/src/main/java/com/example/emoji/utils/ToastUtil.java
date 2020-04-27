package com.example.emoji.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 创建时间:2020/4/26
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class ToastUtil {

    public static void toastShort(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
