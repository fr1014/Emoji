package com.example.media.imageselect;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.media.bean.Image;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 创建时间：2020/2/12
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class SelectViewModel extends ViewModel {

    private MutableLiveData<LinkedHashMap<String, List<Image>>> imageFolders;

    public MutableLiveData<LinkedHashMap<String, List<Image>>> getImageFolders() {
        if (imageFolders == null) {
            imageFolders = new MutableLiveData<>();
        }
        return imageFolders;
    }

}
