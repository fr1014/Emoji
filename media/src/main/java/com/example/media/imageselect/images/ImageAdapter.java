package com.example.media.imageselect.images;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.media.R;
import com.example.media.bean.Image;
import com.example.media.utils.CommonUtils;
import com.example.media.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2020/2/14
 * 作者:15463
 * 邮箱:1546352238@qq.com
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context context;
    private List<Image> images;
    private SelectImageListener listener;
    private int count = 0;                    //可以选择的图片数量,小于等于0时不限选择的数量
    private int countSelected = 0;            //已选择的图片数量
    private List<Image> imagesSelected;   //选择的图片集合

    public ImageAdapter(Context context, SelectImageListener listener) {
        this.context = context;
        images = new ArrayList<>();
        this.listener = listener;
        imagesSelected = new ArrayList<>();
    }

    //设置图片源
    public void setImages(List<Image> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    //设置最多可选择的图片数量
    public void setCount(int count) {
        this.count = count;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {
        if (images != null) {
            GlideUtils.load(images.get(position).getPath(), holder.imageView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Image image = images.get(holder.getAdapterPosition());
                if (imagesSelected.contains(image)) {  //点击已添加的图片，取消选择
                    imagesSelected.remove(image);
                    countSelected--;
                    holder.unSelect.setVisibility(View.VISIBLE);
                    holder.selected.setVisibility(View.INVISIBLE);
                    listener.selectImage(imagesSelected);
                } else if (count == 0) {
                    holder.unSelect.setVisibility(View.INVISIBLE);
                    holder.selected.setVisibility(View.VISIBLE);
                    imagesSelected.add(image);
                    listener.selectImage(imagesSelected);
                } else if (countSelected < count) {       //可以添加图片
                    holder.unSelect.setVisibility(View.INVISIBLE);
                    holder.selected.setVisibility(View.VISIBLE);
                    imagesSelected.add(image);
                    countSelected++;
                    listener.selectImage(imagesSelected);
                } else {
                    CommonUtils.ToastShort(context, "只能选择" + count + "张图片");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return images != null ? images.size() : 0;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView unSelect;
        ImageView selected;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            unSelect = itemView.findViewById(R.id.iv_unSelect);
            selected = itemView.findViewById(R.id.iv_selected);
        }
    }

    public interface SelectImageListener {
        void selectImage(List<Image> imagesSelected);
    }
}
