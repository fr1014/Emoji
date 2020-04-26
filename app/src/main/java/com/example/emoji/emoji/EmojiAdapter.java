package com.example.emoji.emoji;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

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

    public EmojiAdapter(Context context) {
        this.context = context;
        activity = (EmojiActivity) context;
        this.layoutId = R.layout.item_emoji;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.getInstance(context,layoutId,parent);
    }

    @Override
    protected void convert(ViewHolder holder, EmojiEntity emojiEntity, int position) {
        ImageView emoji = holder.getView(R.id.iv_emoji);
        GlideUtils.load(emojiEntity.getImage(),emoji);
        emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                activity.nativeShareTool.shareImageToQQ(ImageUtil.getBitmapFromByte(emojiEntity.getImage()));
                activity.nativeShareTool.shareImageToQQ(Resource.getInstance(context).getPicFile(emojiEntity.getImage()));

            }
        });
    }

}
