package com.example.media.utils;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.media.R;

import java.io.File;

/**
 * 创建时间:2020/3/10
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class GlideUtils {

//    public static void load(String path, ImageView view) {
//        Glide
//                .with(view.getContext())
//                .load(FileManager.getImageContentUri(view.getContext(), path))
//                .into(view);
//    }

    public static void load(String path, ImageView view, Application application) {
        Glide
                .with(view.getContext())
                .load(FileManager.file2Uri(application, new File(path)))
                .into(view);
    }

    public static void load(String path, ImageView view) {
        Glide
                .with(view.getContext())
                .load(new File(path))
                .into(view);
    }

    public static void load(Bitmap bitmap, ImageView view) {
        Glide
                .with(view.getContext())
                .load(bitmap)
                .into(view);
    }

    public static void load(byte[] bytes,ImageView view) {
        Glide
                .with(view.getContext())
                .load(bytes)
                .into(view);
    }


    //加载视频的第一帧动画
//    public static void loadThumb(String path, ImageView view) {
//        Glide
//                .with(view.getContext())
//                .load(Uri.fromFile(new File(path)))
//                .placeholder(R.drawable.ic_placeholder)
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                .into(view);
//    }
}
