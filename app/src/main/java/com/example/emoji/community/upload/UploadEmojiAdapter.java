package com.example.emoji.community.upload;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emoji.R;
import com.example.emoji.base.BaseRecyclerViewAdapter;
import com.example.emoji.community.CommunityViewModel;
import com.example.media.bean.Image;
import com.example.media.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2020/6/17
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class UploadEmojiAdapter extends BaseRecyclerViewAdapter<Image> {
    private Context context;
    private CommunityViewModel viewModel;

    public UploadEmojiAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void setData(List<Image> data) {
        if (data == null) {
            mData.clear();
            return;
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void setViewModel(ViewModel viewModel){
        this.viewModel = (CommunityViewModel) viewModel;
    }

    public void removeData(Image data) {
        mData.remove(data);
        List<Image> newData = new ArrayList<>(mData);
        mData.clear();
        setData(newData);
        viewModel.getUpLoadImagesLiveData().postValue(newData);
        Log.d(TAG, "----removeData: " + mData.size());
    }

    @Override
    public BaseRecyclerViewHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_emoji, parent, false);
        return new EmojiViewHolder(view);
    }

    private static final String TAG = "EmojiAdapter";

    @Override
    protected void bindData(BaseRecyclerViewHolder holder, Image data) {
        EmojiViewHolder emojiViewHolder = (EmojiViewHolder) holder;
        emojiViewHolder.del.setVisibility(View.VISIBLE);
        GlideUtils.load(data.getPath(), emojiViewHolder.emoji);
        Log.d(TAG, "----bindData: " + mData.indexOf(data));
        emojiViewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "----onClick: "+mData.indexOf(data));
                removeData(data);
            }
        });
    }

//    @Override
//    public int getItemCount() {
//        return mData == null ? 1 : mData.size() + 1;
//    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

    }

    static class EmojiViewHolder extends BaseRecyclerViewHolder {
        public ImageView emoji;
        private ImageView del;

        public EmojiViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void findView(View v) {
            emoji = v.findViewById(R.id.iv_emoji);
            del = v.findViewById(R.id.iv_del);
        }
    }


}
