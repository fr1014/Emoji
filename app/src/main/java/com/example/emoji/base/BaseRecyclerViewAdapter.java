package com.example.emoji.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2020/6/16
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseRecyclerViewHolder> {
    //normal item view type
    public static final int TYPE_NORMAL = 0;
    //header view type
    public static final int TYPE_HEADER = 1;
    //footer view type
    public static final int TYPE_FOOTER = 2;
    private View mHeaderView;  //头部视图
    private View mFooterView;  //尾部视图
    protected OnItemClickListener<T> onItemClickListener;
    protected List<T> mData = new ArrayList<>();

    public void setData(List<T> data) {
        if (data == null) {
            mData.clear();
            return;
        }
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        if (mData != null) {
            mData.clear();
        }
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * add header view
     *
     * @param headerView view
     */
    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
        notifyItemInserted(0);
    }

    /**
     * add header view
     *
     * @param context
     * @param layoutId layout res
     */
    public void setHeaderView(Context context, int layoutId) {
        this.mHeaderView = LayoutInflater.from(context).inflate(layoutId, null);
        notifyItemInserted(0);
    }

    public void setHeaderView(Context context, int layoutId, View root) {
        if (context == null && layoutId < 0) {
            return;
        }
        this.mHeaderView = LayoutInflater.from(context).inflate(layoutId, (ViewGroup) root, false);
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setFooterView(View footerView) {
        this.mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    public void setFooterView(Context context, int layoutId,View root) {
        if (context == null && layoutId < 0) {
            return;
        }
        this.mFooterView = LayoutInflater.from(context).inflate(layoutId, (ViewGroup) root,false);
        notifyItemInserted(getItemCount() - 1);
    }

    public View getFooterView() {
        return mFooterView;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return TYPE_HEADER;
        }
        if (mFooterView != null && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    private int getRealPosition(BaseRecyclerViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER){
            return new FooterViewHolder(mFooterView);
        }
        return onCreateRecyclerViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder holder, int position) {
        //如果是header footer view就直接返回,不需要绑定数据
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        if (getItemViewType(position) == TYPE_FOOTER) {
            return;
        }
        final int pos = getRealPosition(holder);
        final T data = mData.get(pos);
        bindData(holder, data);

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(pos, data);
                }
            });
        }
    }

    public abstract BaseRecyclerViewHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType);

    /**
     * 绑定数据到视图
     *
     * @param holder
     * @param data   数据
     */
    protected abstract void bindData(BaseRecyclerViewHolder holder, T data);

    @Override
    public int getItemCount() {
        int count = mData.size();
        if (mHeaderView != null) {
            count++;
        }
        if (mFooterView != null) {
            count++;
        }
        return count;
    }

    //重写使得GridLayoutManager中的HeaderView和FooterView可以独占一行
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = ((GridLayoutManager) manager);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == TYPE_HEADER) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (getItemViewType(position) == TYPE_FOOTER) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    public static abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
        public BaseRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            findView(itemView);
        }

        public abstract void findView(View v);
    }

    public static class HeaderViewHolder extends BaseRecyclerViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void findView(View v) {

        }

    }

    public static class FooterViewHolder extends BaseRecyclerViewHolder{

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void findView(View v) {

        }
    }

    /**
     * item 点击事件接口
     *
     * @param <T> position数据实际位置
     */
    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }
}
