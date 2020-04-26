package com.example.media.imageselect;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 创建时间：2020/2/9
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 *
 * 默认分隔线实现类只支持布局管理器为 LinearLayoutManager
 */
public class CustomItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private int mDividerHeight;

    public CustomItemDecoration(){
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);   //分割线默认为黑色
        mDividerHeight = 3; //默认分割线的高度为3px
    }

    /**
     * 设置分割线的高度
     * @param dividerHeight 分割线高度
     */
    public void setDividerHeight(int dividerHeight){
        this.mDividerHeight = dividerHeight;
    }

    /**
     * 设置分割线的颜色
     * @param color 颜色
     */
    public void setDividerColor(int color){
        mPaint.setColor(color);
    }

    /**
     * recyclerView 的item区域理解：
     * 每一个itemView其实是一个“回”字形布局，即是由两个矩形框构成，内层矩形显示itemView内容，外层矩形限制
     * 显示范围。默认情况下两个矩形的宽高是一样的，两者重叠。 outRect就是内层view的显示区域，
     * 调用outRect.set（Rect rect）或者outRect.set(left, top, right, bottom)就是修改内层view的区域大小，
     * 也可以理解成给内层view设置margin值，其作用与在itemView的布局文件设置margin一样。
     * 针对的是itemView本身
     *
     * @param outRect 绘制itemView的区域
     * @param view    ==itemView
     * @param parent  即是指recyclerView
     * @param state   指recyclerView的状态
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //outRect.set(); 该方法其实相当于给itemView设置margin
        outRect.set(0, 0, 0, 10);
    }

    /**
     * 该方法是指在itemView的区域内设置范围并绘制内容， 可绘制区域是itemView的（外层范围-内层范围）的矩形范围
     * 如果设置的绘制区域超出可绘制区域，那么将会给内层的view遮挡
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        //获取子View的数量
        int childCount = parent.getChildCount();
        //遍历每个ItemView，分别获取它们的位置信息
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            //获取itemView的参数
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();
            //获取分割线的左、顶、右、下的坐标点
            int left = parent.getPaddingLeft();
            int top = childView.getBottom() + layoutParams.bottomMargin;
            int right = parent.getWidth() - parent.getPaddingRight();
            int bottom = top + mDividerHeight;

            //绘制分割线
            c.drawRect(left,top,right,bottom,mPaint);
        }
    }

    /**
     * 同样是绘制内容，但是层次上是在最顶层
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
