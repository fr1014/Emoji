package com.example.emoji.community;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;

import com.example.emoji.MyApplication;
import com.example.emoji.R;
import com.example.emoji.base.BaseBindingFragment;
import com.example.emoji.community.upload.CommunityActivity;
import com.example.emoji.databinding.FragmentCommunityBinding;
import com.example.emoji.utils.ToastUtil;
import com.example.media.imageselect.CustomItemDecoration;

import java.util.Objects;

import cn.bmob.v3.BmobUser;

public class CommunityFragment extends BaseBindingFragment<FragmentCommunityBinding, CommunityViewModel> implements View.OnClickListener {

    private CommunityAdapter adapter;

    public CommunityFragment() {
        // Required empty public constructor
    }

    public static CommunityFragment getInstance() {
        CommunityFragment fragment = new CommunityFragment();
        return fragment;
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(CommunityViewModel.class);
    }

    @Override
    protected FragmentCommunityBinding getViewBinding() {
        return FragmentCommunityBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView(View view) {
        mBinding.ivUpload.setOnClickListener(this);
        mBinding.rvCommunity.setLayoutManager(new LinearLayoutManager(getContext()));
        CustomItemDecoration itemDecoration = new CustomItemDecoration();
        itemDecoration.setDividerColor(ContextCompat.getColor(MyApplication.getInstance(), R.color.itemDecoration));
        itemDecoration.setDividerHeight(12);
        mBinding.rvCommunity.addItemDecoration(itemDecoration);
        adapter = new CommunityAdapter(getContext());
//        adapter.setData(data);
        mBinding.rvCommunity.setAdapter(adapter);
    }

    private static final String TAG = "CommunityFragment";

    @Override
    protected void initData() {

        viewModel.getQueryAllPostLiveData().observe(this, posts -> {
            Log.d(TAG, "----initData: " + posts.size());
            adapter.setData(posts);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_upload:
                if (BmobUser.isLogin()) {
                    startActivity(this, CommunityActivity.class);
                } else {
                    ToastUtil.toastShort("请先登录!!!");
                }

                break;
        }
    }


}