package com.example.emoji.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

/**
 * 创建时间:2020/6/17
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public abstract class BaseBindingFragment<VB extends ViewBinding,VM extends ViewModel> extends Fragment {
    protected VM viewModel;
    protected VB mBinding;

    public BaseBindingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "----onCreateView: ");
        initViewModel();
        mBinding = getViewBinding(container);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "----onViewCreated: ");
        initView(view);
        initData();
    }

    protected void initViewModel(){

    }

    protected abstract VB getViewBinding(ViewGroup container);

    protected abstract void initView(View view);

    protected abstract void initData();

    public void startActivity(Fragment fragment, Class clzz){
        Intent intent = new Intent(fragment.getActivity(),clzz);
        startActivity(intent);
    }

    private static final String TAG = "BaseBindingFragment";
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "----onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "----onCreate: ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "----onActivityCreated: ");
    }

    @Override
    public void onStart() {
        Log.d(TAG, "----onStart: ");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "----onResume: ");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "----onPause: ");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "----onStop: ");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "----onDestroyView: ");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "----onDestroy: ");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "----onDetach: ");
        super.onDetach();
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
