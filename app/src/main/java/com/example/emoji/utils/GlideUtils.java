package com.example.emoji.utils;

import android.app.Application;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.emoji.R;
import com.example.media.utils.FileUtils;
import com.example.media.utils.StringUtils;

import java.io.File;

/**
 * 创建时间:2020/3/10
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class GlideUtils {

    public static void load(ImageView view, String url) {
        if (!StringUtils.isEmpty(url)) {
            Glide.with(view.getContext())
                    .load(url)
                    .error(R.drawable.iv_head)
                    .placeholder(R.drawable.iv_head)
                    .into(view);
        }
    }

}
