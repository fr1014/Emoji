package com.example.emoji.photo;

import androidx.fragment.app.Fragment;

import android.view.View;
import android.view.ViewGroup;

import com.example.emoji.base.BaseBindingFragment;
import com.example.emoji.databinding.FragmentPhotoBinding;

public class PhotoFragment extends BaseBindingFragment<FragmentPhotoBinding,PhotoViewModel> {

    public PhotoFragment() {
    }

    public static Fragment getInstance(){
        return new PhotoFragment();
    }

    @Override
    protected FragmentPhotoBinding getViewBinding(ViewGroup container) {
        return FragmentPhotoBinding.inflate(getLayoutInflater(),container,false);
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }
}