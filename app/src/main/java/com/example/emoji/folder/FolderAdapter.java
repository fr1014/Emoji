package com.example.emoji.folder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.emoji.Constants;
import com.example.emoji.R;
import com.example.emoji.base.MyRecyclerViewAdapter;
import com.example.emoji.data.entity.room.entity.FolderEntity;
import com.example.emoji.folder.emoji.EmojiActivity;
import com.example.media.utils.GlideUtils;

import java.util.ArrayList;

/**
 * 创建时间:2020/4/20
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class FolderAdapter extends MyRecyclerViewAdapter<FolderEntity> {

    private FolderFragment fragment;

    public FolderAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = (FolderFragment) fragment;
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
        TextView tvDel = holder.getView(R.id.tv_delete);
        if (folderEntity.getBytes() == null) {
            imageView.setImageResource(R.drawable.ic_launcher);
        } else {
            GlideUtils.load(folderEntity.getBytes(), imageView);
        }
        textView.setText(folderEntity.getName());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EmojiActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.FOLDER_ENTITY, folderEntity);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ivUpdate.setVisibility(View.VISIBLE);
                tvEdit.setVisibility(View.VISIBLE);
                tvDel.setVisibility(View.VISIBLE);
                return true;
            }
        });

        tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                final AlertDialog dialog = builder.setIcon(R.drawable.ic_launcher)
                        .setTitle("提示")
                        .setMessage("确认删除该文件夹？")
                        // 或者在这里处理一些事件
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fragment.viewModel.delete(folderEntity);
                                tvDel.setVisibility(View.INVISIBLE);
                                ivUpdate.setVisibility(View.INVISIBLE);
                                tvEdit.setVisibility(View.INVISIBLE);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create();
                dialog.show();

            }
        });

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDel.setVisibility(View.INVISIBLE);
                ivUpdate.setVisibility(View.INVISIBLE);
                tvEdit.setVisibility(View.INVISIBLE);
                if (fragment != null) {
                    View view = fragment.motionView;
                    if (view != null) {
                        view.setVisibility(View.INVISIBLE);
                    }
                    fragment.addFragment(AddFolderFragment.getInstance(folderEntity));
                }
            }
        });


    }

}
