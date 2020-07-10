package com.example.emoji.person;

import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.emoji.MyApplication;
import com.example.emoji.R;
import com.example.emoji.base.BaseBindingFragment;
import com.example.emoji.community.CommunityViewModel;
import com.example.emoji.community.upload.CommunityActivity;
import com.example.emoji.community.upload.CommunityAdapter;
import com.example.emoji.databinding.FragmentCommunityBinding;
import com.example.emoji.utils.ToastUtil;
import com.example.media.imageselect.CustomItemDecoration;

import java.util.Objects;

import cn.bmob.v3.BmobUser;

public class PersonCommunityFragment extends BaseBindingFragment<FragmentCommunityBinding, CommunityViewModel> implements View.OnClickListener {

    private CommunityAdapter adapter;

    public PersonCommunityFragment() {
        // Required empty public constructor
    }

    public static PersonCommunityFragment getInstance() {
        PersonCommunityFragment fragment = new PersonCommunityFragment();
        return fragment;
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(CommunityViewModel.class);
    }

    @Override
    protected FragmentCommunityBinding getViewBinding(ViewGroup container) {
        return FragmentCommunityBinding.inflate(getLayoutInflater(), container, false);
    }

    @Override
    protected void initView(View view) {
        mBinding.ivUpload.setOnClickListener(this);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mBinding.rvCommunity.setLayoutManager(new LinearLayoutManager(getContext()));
        CustomItemDecoration itemDecoration = new CustomItemDecoration();
        itemDecoration.setDividerColor(ContextCompat.getColor(MyApplication.getInstance(), R.color.itemDecoration));
        itemDecoration.setDividerHeight(12);
        mBinding.rvCommunity.addItemDecoration(itemDecoration);
        adapter = new CommunityAdapter(getContext());
        mBinding.rvCommunity.setAdapter(adapter);
    }

    @Override
    protected void initData() {

        viewModel.getQueryPostLiveData().observe(this, posts -> {
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

    @Override
    public boolean onBackPressed() {
        if (getParentFragment() != null) {
            getParentFragment().getChildFragmentManager().popBackStack();
            return true;
        }
        return false;
    }
}