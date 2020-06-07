package com.example.emoji.folder.emoji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModel;

import com.example.emoji.R;
import com.example.emoji.base.MyRecyclerViewAdapter;
import com.example.emoji.data.room.entity.EmojiEntity;
import com.example.media.utils.FileUtils;
import com.example.media.utils.GlideUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

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

    public void setViewModel(ViewModel viewModel) {
        this.viewModel = (EmojiViewModel) viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.getInstance(context, layoutId, parent);
    }

    @Override
    protected void convert(ViewHolder holder, EmojiEntity emojiEntity, int position) {
        ImageView emoji = holder.getView(R.id.iv_emoji);
        TextView tvDelete = holder.getView(R.id.tv_del);
        GlideUtils.load(emojiEntity.getBytes(), emoji);
        emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                activity.nativeShareTool.shareImageToQQ(emojiEntity.getPath());
                customImgTvDialog(emojiEntity);
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
                if (viewModel != null) {
                    viewModel.delete(emojiEntity);
                }
            }
        });


    }

    /**
     * 自定义图文对话框实现
     **/
    private void customImgTvDialog(EmojiEntity emojiEntity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.alertdialog_share, null);

        CircleImageView cvQQ = dialogView.findViewById(R.id.qq);
        CircleImageView cvWeChat = dialogView.findViewById(R.id.wechat);

        final AlertDialog dialog = builder.setIcon(R.drawable.ic_launcher).setTitle("分享")
                .setView(dialogView)
                // 或者在这里处理一些事件
//                .setPositiveButton("OK", null)
                .setNegativeButton("取消", null)
                .create();
        dialog.show();

        // 这里可以处理一些点击事件
        cvQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.nativeShareTool.shareImageToQQ(FileUtils.byte2File(emojiEntity.getBytes(), Objects.requireNonNull(context.getExternalCacheDir()).getAbsolutePath(), "emoji"));
                dialog.dismiss();
            }
        });

        cvWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.nativeShareTool.shareWechatFriend(new File(FileUtils.byte2File(emojiEntity.getBytes(), Objects.requireNonNull(context.getExternalCacheDir()).getAbsolutePath(), "emoji")), true);
                dialog.dismiss();
            }
        });
    }

}
