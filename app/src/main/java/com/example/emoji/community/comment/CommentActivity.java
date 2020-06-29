package com.example.emoji.community.comment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.emoji.base.BaseBindingActivity;
import com.example.emoji.data.bmob.Comment;
import com.example.emoji.data.bmob.MyUser;
import com.example.emoji.data.bmob.Post;
import com.example.emoji.databinding.ActivityCommentBinding;
import com.example.emoji.listener.CustomClickListener;
import com.example.emoji.utils.ToastUtil;
import com.example.media.bean.Image;
import com.example.media.imageselect.images.ImageSelectActivity;
import com.example.media.utils.ImageSelector;
import com.example.media.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class CommentActivity extends BaseBindingActivity<ActivityCommentBinding, CommentViewModel> {

    private Post post;
    private String objectId;
    private CommentAdapter adapter;
    private List<Image> allImages = new ArrayList<>(); //将要评论的图片

    @Override
    protected ActivityCommentBinding getViewBinding() {
        return ActivityCommentBinding.inflate(getLayoutInflater());
    }

    private static final String TAG = "CommentActivity";

    @Override
    protected void initBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            post = bundle.getParcelable("Post");
            objectId = bundle.getString("id");
            post.setObjectId(objectId);
        }

    }

    @Override
    public void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CommentViewModel.class);
    }

    @Override
    protected void initView() {
        initRecyclerView();
        onClick();
    }

    private void initRecyclerView() {
        adapter = new CommentAdapter(this);
        mBinding.rvComment.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvComment.setAdapter(adapter);
    }

    private void onClick() {
        mBinding.ivBack.setOnClickListener(v -> {
            this.finish();
        });

        mBinding.ivSelect.setOnClickListener(v -> {
            ImageSelectActivity.startActivity(this, 1, 9 - allImages.size());
        });

        mBinding.tvSend.setOnClickListener(new CustomClickListener() {
            @Override
            protected void onSingleClick(View view) {
                String content = mBinding.etComment.getText().toString();
                if (StringUtils.isEmpty(content)) {
                    ToastUtil.toastShort("评论的内容不能为空!!!");
                } else {
                    MyUser user = BmobUser.getCurrentUser(MyUser.class);
                    final Comment comment = new Comment();
                    comment.setContent(content);
                    comment.setUser(user);
                    comment.setPost(post);
                    viewModel.saveComment(comment);
                }
            }
        });
    }

    @Override
    public void initData() {

        viewModel.getComment(post);

        viewModel.getComments().observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                adapter.setData(comments);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1) {
                List<Image> images = data.getParcelableArrayListExtra(ImageSelector.IMAGE_SELECTED);
                if (images != null) {
                    allImages.addAll(images);
                }
            }
        }
    }
}