package com.example.emoji.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emoji.data.room.entity.FolderEntity;

import java.util.List;

/**
 * 创建时间:2020/4/20
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public abstract class MyRecyclerViewAdapter<T> extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{

    protected Context context;
    protected int layoutId;
    protected List<T> list;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public MyRecyclerViewAdapter() {

    }

    //设置数据源
    public void setData(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public MyRecyclerViewAdapter(Context context, List<T> list, int layoutId) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.getInstance(context, layoutId, parent);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list.size() > 0) {
            convert(holder, list.get(position), position);
        }
        //单击事件
        if (mOnItemClickListener !=null){
            holder.mContentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.mContentView,position);
                }
            });
        }
        //长按事件
        if (mOnItemLongClickListener!=null){
            holder.mContentView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.mContentView,position);
                    return true;//表示消费了此长按事件，不会再传递
                }
            });
        }
    }

    protected abstract void convert(ViewHolder holder, T t, int position);

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View mContentView;
        private SparseArray<View> mViews;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mContentView = itemView;
            mViews = new SparseArray<>();
        }

        public static ViewHolder getInstance(Context context, int layoutId, ViewGroup parent) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            return new ViewHolder(itemView);
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mContentView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }
    }

}
