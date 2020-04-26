package com.example.media.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 创建时间:2020/2/16
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class CommonUtils {

    public static void ToastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void ToastLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 判断字符串是否为空
     *
     * @param s 要判断的字符串
     * @return true 不为空
     */
    public static Boolean isNotEmptyString(String s) {
        return s != null && s.length() > 0;
    }

    /**
     * 将时长数为毫秒的转化为分钟和秒
     *
     * @param duration 毫秒
     * @return 分钟和秒
     */
    public static String timeParse(long duration) {
        String time = "";

        long minute = duration / 60000;
        long seconds = duration % 60000;

        long second = Math.round((float) seconds / 1000);

        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";

        if (second < 10) {
            time += "0";
        }
        time += second;

        return time;
    }
}
