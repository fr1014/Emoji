package com.example.emoji.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emoji.R;
import com.example.emoji.base.BaseRecyclerViewAdapter;
import com.example.emoji.community.comment.CommentActivity;
import com.example.emoji.data.bmob.Post;
import com.example.emoji.utils.GlideUtils;
import com.example.emoji.utils.ToastUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 创建时间:2020/6/19
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class CommunityAdapter extends BaseRecyclerViewAdapter<Post> {
    private Context context;

    public CommunityAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseRecyclerViewHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_community, parent, false);
        return new CommunityViewHolder(view);
    }

    @Override
    protected void bindData(BaseRecyclerViewHolder holder, Post data) {
        CommunityViewHolder communityViewHolder = (CommunityViewHolder) holder;
        GlideUtils.load(communityViewHolder.headPic, data.getAuthor().getHeadPicUrl());
        communityViewHolder.name.setText(data.getAuthor().getUsername());
        communityViewHolder.content.setText(data.getContent());

        initRvEmoji(holder, data, communityViewHolder);
    }

    //初始化rvEmoji
    private void initRvEmoji(BaseRecyclerViewHolder holder, Post data, CommunityViewHolder communityViewHolder) {
        communityViewHolder.rvEmoji.setLayoutManager(new GridLayoutManager(context, 3));

        View footerView = LayoutInflater.from(context).inflate(R.layout.item_community_footer, (ViewGroup) holder.itemView.getRootView(), false);
        CommunityEmojiAdapter adapter = new CommunityEmojiAdapter(context);
        adapter.setFooterView(footerView);
        communityViewHolder.rvEmoji.setAdapter(adapter);
        adapter.setData(data.getImages());

        itemOnClick(footerView, data);
    }

    //处理footerView上的点击事件
    private void itemOnClick(View footerView, Post data) {
        footerView.findViewById(R.id.iv_share).setOnClickListener(v -> {
            ToastUtil.toastShort("分享");
        });

        footerView.findViewById(R.id.iv_comment).setOnClickListener(v -> {
            Intent intent = new Intent(context, CommentActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("Post", data);
            bundle.putString("id",data.getObjectId());
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

        footerView.findViewById(R.id.iv_good).setOnClickListener(v -> {
            ToastUtil.toastShort("点赞");
        });

        footerView.findViewById(R.id.iv_bad).setOnClickListener(v -> {
            ToastUtil.toastShort("差评");
        });
    }

    static class CommunityViewHolder extends BaseRecyclerViewHolder {
        private CircleImageView headPic;
        private TextView name;
        private TextView content;
        private RecyclerView rvEmoji;

        public CommunityViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void findView(View v) {
            headPic = v.findViewById(R.id.cv_head);
            name = v.findViewById(R.id.tv_name);
            content = v.findViewById(R.id.tv_message);
            rvEmoji = v.findViewById(R.id.rv_emoji);
        }
    }
}

class CommunityEmojiAdapter extends BaseRecyclerViewAdapter<String> {
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