package com.customview;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 创建时间:2020/4/14
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class UiUtil {

    // 获取最大宽度
    public static int getMaxWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    // 获取最大高度
    public static int getMaxHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    // 获取状态栏高度
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    // 获取导航栏高度
    public static int getNavigationBarHeight(Context context) {
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        } else
            return 0;
    }
}
