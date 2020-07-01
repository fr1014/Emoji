package com.example.emoji.community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.emoji.R;
import com.example.emoji.base.BaseRecyclerViewAdapter;
import com.example.emoji.utils.GlideUtils;

/**
 * 创建时间:2020/6/29
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class CommunityEmojiAdapter extends BaseRecyclerViewAdapter<String> {
    private Context context;

    public CommunityEmojiAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseRecyclerViewHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_emoji, parent, false);
        return new EmojiViewHolder(view);
    }

    @Override
    protected void bindData(BaseRecyclerViewHolder holder, String data) {
        EmojiViewHolder emojiViewHolder = (EmojiViewHolder) holder;
        GlideUtils.load(emojiViewHolder.emoji, data);
    }

    static class EmojiViewHolder extends BaseRecyclerViewHolder {
        private ImageView emoji;

        public EmojiViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void findView(View v) {
            emoji = v.findViewById(R.id.iv_emoji);
        }
    }
}
