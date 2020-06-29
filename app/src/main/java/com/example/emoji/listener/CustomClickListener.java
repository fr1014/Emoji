package com.example.emoji.listener;

import android.view.View;

import com.example.emoji.utils.ToastUtil;

/**
 * 创建时间:2020/6/20
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public abstract class CustomClickListener implements View.OnClickListener{
    private long mLastClickTime;
    private long timeInterval = 1000L;

    public CustomClickListener(){

    }

    //自设点击间隔（默认为1秒）
    public CustomClickListener(long timeInterval){
        this.timeInterval = timeInterval;
    }

    @Override
    public void onClick(View v) {
        long nowTime = System.currentTimeMillis();
        if (nowTime -mLastClickTime > timeInterval){
            //单次点击事件
            onSingleClick(v);
            mLastClickTime = nowTime;
        }else {
            ToastUtil.toastShort("不要连续点击!!!");
            //快速点击事件
//            onFastClick(v);
        }
    }

    protected void onFastClick(View view){

    }

    protected abstract void onSingleClick(View view);
}
