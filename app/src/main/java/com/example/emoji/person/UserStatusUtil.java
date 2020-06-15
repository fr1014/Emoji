package com.example.emoji.person;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 创建时间:2020/6/8
 * 作者:fr
 * 邮箱:1546352238@qq.com
 *
 * 判断用户的登录状态
 *
 * 如果在 Fragment 中使用SharedPreferences 时，
 * 需要放在onAttach(Activity activity)里面进行SharedPreferences的初始化，
 * 否则会报空指针 即 getActivity()会可能返回null ！
 */
public class UserStatusUtil {

    public static void editLoginStatus(Application application,Boolean status){
        //步骤1：创建一个SharedPreferences对象
        SharedPreferences sharedPreferences= application.getSharedPreferences("login_status", Context.MODE_PRIVATE);
        //步骤2： 实例化SharedPreferences.Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //步骤3：将获取过来的值放入文件
        editor.putBoolean("status",status);
        //步骤4：提交
        editor.apply();
    }

    //读取登录状态默认为false
    public static Boolean readLoginStatus(Application application){
        SharedPreferences sharedPreferences= application.getSharedPreferences("login_status", Context .MODE_PRIVATE);
        boolean status = sharedPreferences.getBoolean("status", false);
        return status;
    }
}
