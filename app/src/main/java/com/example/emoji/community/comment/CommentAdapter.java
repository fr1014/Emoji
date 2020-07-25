package com.example.emoji.community.comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emoji.R;
import com.example.emoji.base.BaseRecyclerViewAdapter;
import com.example.emoji.community.CommunityEmojiAdapter;
import com.example.emoji.data.entity.bmob.Comment;
import com.example.emoji.utils.GlideUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 创建时间:2020/6/29
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class CommentAdapter extends BaseRecyclerViewAdapter<Comment> {
    private Context context;

    public CommentAdapter(Context context){
        this.context = context;
    }

    @Override
    public BaseRecyclerViewHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_community,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    protected void bindData(BaseRecyclerViewHolder holder, Comment data) {
        CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
        GlideUtils.load(commentViewHolder.headPic,data.getUser().getHeadPicUrl());
        commentViewHolder.name.setText(data.getUser().getUsername());
        commentViewHolder.comment.setText(data.getContent());

        initRvEmoji(data,commentViewHolder);
    }

    private void initRvEmoji(Comment data,CommentViewHolder commentViewHolder) {
        commentViewHolder.rvEmoji.setLayoutManager(new GridLayoutManager(context,3));
        CommunityEmojiAdapter adapter = new CommunityEmojiAdapter(context);
        adapter.setData(data.getImages());
        commentViewHolder.rvEmoji.setAdapter(adapter);
    }

    static class CommentViewHolder extends BaseRecyclerViewHolder{

        private CircleImageView headPic;
        private TextView name;
        private TextView comment;
        private RecyclerView rvEmoji;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void findView(View v) {
            headPic = v.findViewById(R.id.cv_head);
            name = v.findViewById(R.id.tv_name);
            comment = v.findViewById(R.id.tv_message);
            rvEmoji = v.findViewById(R.id.rv_emoji);
        }
    }
}
