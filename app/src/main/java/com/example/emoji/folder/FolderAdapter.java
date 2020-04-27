package com.example.emoji.folder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.emoji.Constants;
import com.example.emoji.data.room.entity.FolderEntity;
import com.example.emoji.R;
import com.example.emoji.base.MyRecyclerViewAdapter;
import com.example.emoji.emoji.EmojiActivity;
import com.example.media.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2020/4/20
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class FolderAdapter extends MyRecyclerViewAdapter<FolderEntity> {

    private HomeActivity homeActivity;

    public FolderAdapter(Context context) {
        this.context = context;
        homeActivity = (HomeActivity) context;
        this.layoutId = R.layout.item_emoji_folder;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.getInstance(context, layoutId, parent);
    }

    @Override
    protected void convert(ViewHolder holder, FolderEntity folderEntity, int position) {
        ImageView imageView = holder.getView(R.id.iv_folder);
        ImageView ivUpdate = holder.getView(R.id.iv_update);
        TextView textView = holder.getView(R.id.tv_folder);
        TextView tvEdit = holder.getView(R.id.tv_edit);
        if (folderEntity.getPath() == null) {
            imageView.setImageResource(R.drawable.ic_launcher);
        } else {
            GlideUtils.load(folderEntity.getPath(), imageView);
        }
        textView.setText(folderEntity.getName());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EmojiActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.FOLDER_ENTITY,folderEntity);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ivUpdate.setVisibility(View.VISIBLE);
                tvEdit.setVisibility(View.VISIBLE);
                return true;
            }
        });

        ivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivUpdate.setVisibility(View.INVISIBLE);
                tvEdit.setVisibility(View.INVISIBLE);
                if (homeActivity != null) {
                    View view = homeActivity.getMotionView();
                    if (view != null) {
                        view.setVisibility(View.INVISIBLE);
                    }
                    homeActivity.addFragment(AddFolderFragment.getInstance(folderEntity));
                }
            }
        });
    }

}
