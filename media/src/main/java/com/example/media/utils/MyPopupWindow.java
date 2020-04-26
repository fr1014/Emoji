package com.example.media.utils;

import android.view.View;
import android.widget.PopupWindow;

/**
 * 创建时间:2020/3/21
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class MyPopupWindow {

    public static void showPopupWindow(View contentView, PopupWindow popupWindow, View showAsDropDown){
        popupWindow.setContentView(contentView);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(showAsDropDown);
    }

    public static void dismiss(PopupWindow popupWindow){
        popupWindow.dismiss();
    }
}
