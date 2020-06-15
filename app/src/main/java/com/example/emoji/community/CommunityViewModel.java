package com.example.emoji.community;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * 创建时间:2020/6/8
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class CommunityViewModel extends AndroidViewModel {
    private Application application;

    public CommunityViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

}
