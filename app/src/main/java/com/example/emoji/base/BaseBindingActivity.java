package com.example.emoji.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

/**
 * 创建时间:2020/6/17
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public abstract class BaseBindingActivity<VB extends ViewBinding,VM extends ViewModel> extends AppCompatActivity {
    public VM viewModel;
    protected VB mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = getViewBinding();
        setContentView(mBinding.getRoot());
        initViewModel();
        initView();
        initBundle();
        initData();
    }

    protected abstract VB getViewBinding();

    protected abstract void initView();

    protected void initBundle(){

    }

    public void initViewModel(){

    }

    public void initData(){

    }

    public void startActivity(Activity activity, Class clzz){
        Intent intent = new Intent(activity,clzz);
        startActivity(intent);
    }
}
