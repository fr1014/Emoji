package com.example.emoji.emoji;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.emoji.R;
import com.example.emoji.base.MyRecyclerViewAdapter;
import com.example.emoji.data.room.entity.EmojiEntity;
import com.example.emoji.utils.ImageUtil;
import com.example.emoji.utils.shareutils.NativeShareTool;
import com.example.emoji.utils.shareutils.Resource;
import com.example.media.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2020/4/23
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class EmojiAdapter extends MyRecyclerViewAdapter<EmojiEntity> {
    private EmojiActivity activity;
    private EmojiViewModel viewModel;

    public EmojiAdapter(Context context) {
        this.context = context;
        activity = (EmojiActivity) context;
        this.layoutId = R.layout.item_emoji;
        this.list = new ArrayList<>();
    }

    public void setViewModel(ViewModel viewModel){
        this.viewModel = (EmojiViewModel) viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.getInstance(context,layoutId,parent);
    }

    @Override
    protected void convert(ViewHolder holder, EmojiEntity emojiEntity, int position) {
        ImageView emoji = holder.getView(R.id.iv_emoji);
        TextView tvDelete = holder.getView(R.id.tv_del);
        GlideUtils.load(emojiEntity.getPath(),emoji);
        emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.nativeShareTool.shareImageToQQ(emojiEntity.getPath());
            }
        });

        emoji.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tvDelete.setVisibility(View.VISIBLE);
                return true;
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel!= null){
                    viewModel.delete(emojiEntity);
                }
            }
        });
    }

}
