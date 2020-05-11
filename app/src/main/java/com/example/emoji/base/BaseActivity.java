package com.example.emoji.base;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

/**
 * 创建时间:2020/4/22
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public abstract class BaseActivity<VM extends ViewModel> extends AppCompatActivity {
    public VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initBundle();
        initViewModel();
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initBundle();

    public void initViewModel(){

    }

    public void initData(){

    }
}
