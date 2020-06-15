package com.example.emoji.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

/**
 * 创建时间:2020/6/8
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public abstract class BaseFragment<VM extends ViewModel> extends Fragment {
    protected VM viewModel;

    public BaseFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViewModel();
        return inflater.inflate(getLayoutRes(),container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    protected void initViewModel(){

    }

    protected abstract int getLayoutRes();

    protected abstract void initView(View view);

    protected abstract void initData();

    public void startActivity(Fragment fragment, Class clzz){
        Intent intent = new Intent(fragment.getActivity(),clzz);
        startActivity(intent);
    }

    /*
     * fragment中的返回键
     *
     * 默认返回false，交给Activity处理
     * 返回true：执行fragment中需要执行的逻辑
     * 返回false：执行activity中的 onBackPressed
     * */
    public boolean onBackPressed() {
        return false;
    }

}
