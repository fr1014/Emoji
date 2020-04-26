package com.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ImageView;

import androidx.annotation.Nullable;

/**
 * 创建时间:2020/4/6
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
/*
    1.MotionEvent:触摸事件
    int ACTION_DOWN = 0;    //代表down
    int ACTION_UP   = 1;    //代表up
    int ACTION_MOVE  = 2;   //代表move
    getAction()             //得到事件类型值
    getX()                  //得到事件发生的X轴坐标（相对于当前视图）
    getRawX()               //得到事件发生的X轴坐标（相对于屏幕左顶点）
    getY()                  //得到事件发生的Y轴坐标（相对于当前视图）
    getRawY()               //得到事件发生的Y轴坐标（相对于屏幕左顶点）

    2.Activity
        boolean dispatchTouchEvent(MotionEvent ev)  //分发事件
        boolean onTouchEvent(MotionEvent ev)        //处理事件的回调

    3.View
        boolean dispatchTouchEvent(MotionEvent ev)  //分发事件
        boolean onTouchEvent(MotionEvent ev)        //处理事件的回调
        void setOnTouchListener(OnTouchListener l)  //设置事件监听器
        void setOnClickListener(@Nullable OnClickListener l)    //设置点击监听
        void setOnLongClickListener(@Nullable OnLongClickListener l)    //设置长按监听
        void setOnCreateContextMenuListener(OnCreateContextMenuListener l)  //用于创建菜单

    4.ViewGroup
        boolean dispatchTouchEvent(MotionEvent ev)  //分发事件
        boolean onInterceptTouchEvent(MotionEvent ev) //拦截事件的回调方法
 */
@SuppressLint("AppCompatCustomView")
public class MotionEventImageView extends ImageView {
    private Context context;
    private int lastX;
    private int lastY;
    private int width; //  测量宽度 MotionEventImageView的宽度
    private int height; // 测量高度 MotionEventImageView的高度
    private int maxWidth; //最大宽度 window的宽度
    private int maxHeight; //最大高度 window的高度

    public MotionEventImageView(Context context) {
        super(context);
    }

    public MotionEventImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    private static final String TAG = "MotionEventImageView";

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取屏宽高 和 可是适用范围 （我的需求是可在屏幕内拖动 不超出范围 也不需要隐藏）
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        maxWidth = UiUtil.getMaxWidth(context);
        maxHeight = UiUtil.getMaxHeight(context) - UiUtil.getStatusBarHeight(context) - UiUtil.getNavigationBarHeight(context);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private boolean move = false;  //view是否移动

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "------MotionEventImageView onTouchEvent: " + event.getAction());
        int x = (int) event.getRawX(); //当前视图距离屏幕原点的横坐标x
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;  //按下时视图距离屏幕原点的横坐标x
                lastY = y;
                move = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - lastX; //距离按下时视图的横坐标x移动的数值
                int dy = y - lastY;
                int left = getLeft() + dx;
                int top = getTop() + dy;
                int right = getRight() + dx;
                int bottom = getBottom() + dy;

                //限制left >= 0
                if (left < 0) {
                    right += -left;
                    left = 0;
                }
                //限制top >= 0
                if (top < 0) {
                    bottom += -top;
                    top = 0;
                }
                //限制right <= maxWidth
                if (right > maxWidth) {
                    left -= right - maxWidth;
                    right = maxWidth;
                }
                //限制bottom <= maxHeight
                if (bottom > maxHeight) {
                    top -= bottom - maxHeight;
                    bottom = maxHeight;
                }
                layout(left, top, right, bottom);

                lastX = x;          //保存移动过程中的视图横坐标x的位置
                lastY = y;
                move = true;
                break;

            case MotionEvent.ACTION_UP:
                if (!move) {
                    performClick();
                }
                break;
        }
        return true;
//        return super.onTouchEvent(event);
    }


}
