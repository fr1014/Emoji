package com.example.media.imageselect.images;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.media.R;
import com.example.media.bean.Image;
import com.example.media.utils.GlideUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 创建时间：2020/2/12
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {
    private Context mContext;
    private int mSelectPosition = 0;
    HashMap<String, List<Image>> imageFolders;
    List<String> imgFolderNames;

    private SelectFolderListener listener;

    public FolderAdapter(Context context, SelectFolderListener listener) {
        this.mContext = context;
        this.listener = listener;
        imageFolders = new HashMap<>();
        imgFolderNames = new ArrayList<>();

    }

    public void setImageFolders(HashMap<String, List<Image>> imageFolders) {
        this.imageFolders = imageFolders;
        for (HashMap.Entry<String, List<Image>> stringListEntry : imageFolders.entrySet()) {
            String key;
            key = (String) ((HashMap.Entry) stringListEntry).getKey();
            imgFolderNames.add(key);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FolderAdapter.FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_folder, parent, false);
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FolderAdapter.FolderViewHolder holder, int position) {
        final String name = imgFolderNames.get(position);
        final List<Image> images = imageFolders.get(name);
        if (images != null) {
            int num = images.size();
            String imgPath = images.get(0).getPath();
            holder.tvName.setText(name);
            holder.tvNum.setText("(" + num + ")");
            holder.ivSelected.setVisibility(mSelectPosition == position ? View.VISIBLE : View.INVISIBLE);

            GlideUtils.load(imgPath, holder.ivFirst);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectPosition = holder.getAdapterPosition();
                    listener.selectFolder(name, images);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return imageFolders != null ? imageFolders.size() : 0;
    }

    public static class FolderViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivFirst;
        public ImageView ivSelected;
        public TextView tvName;
        public TextView tvNum;

        public FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFirst = itemView.findViewById(R.id.iv_fist);
            ivSelected = itemView.findViewById(R.id.iv_selected);
            tvName = itemView.findViewById(R.id.tv_name);
            tvNum = itemView.findViewById(R.id.tv_num);
        }
    }

    public interface SelectFolderListener {
        void selectFolder(String name, List<Image> images);
    }
}
