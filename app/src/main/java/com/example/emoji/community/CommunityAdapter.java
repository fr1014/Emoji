package com.example.emoji.community;

import android.content.Context;
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
import com.example.emoji.data.bmob.Post;
import com.example.emoji.utils.GlideUtils;

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
        View view = LayoutInflater.from(context).inflate(R.layout.item_community,parent,false);
        return new CommunityViewHolder(view);
    }

    @Override
    protected void bindData(BaseRecyclerViewHolder holder, Post data) {
        CommunityViewHolder communityViewHolder = (CommunityViewHolder) holder;
        GlideUtils.load(communityViewHolder.headPic,data.getAuthor().getHeadPicUrl());
        communityViewHolder.name.setText(data.getAuthor().getUsername());
        communityViewHolder.content.setText(data.getContent());

        communityViewHolder.rvEmoji.setLayoutManager(new GridLayoutManager(context,3));
        CommunityEmojiAdapter adapter = new CommunityEmojiAdapter(context);

        adapter.setFooterView(context,R.layout.item_community_footer,holder.itemView.getRootView());
        communityViewHolder.rvEmoji.setAdapter(adapter);
        adapter.setData(data.getImages());
    }

    static class CommunityViewHolder extends BaseRecyclerViewHolder{
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

class CommunityEmojiAdapter extends BaseRecyclerViewAdapter<String>{
    private Context context;

    public CommunityEmojiAdapter(Context context){
        this.context = context;
    }

    @Override
    public BaseRecyclerViewHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_emoji,parent,false);
        return new EmojiViewHolder(view);
    }

    @Override
    protected void bindData(BaseRecyclerViewHolder holder, String data) {
        EmojiViewHolder emojiViewHolder = (EmojiViewHolder) holder;
        GlideUtils.load(emojiViewHolder.emoji,data);
    }

    static class EmojiViewHolder extends BaseRecyclerViewHolder{
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