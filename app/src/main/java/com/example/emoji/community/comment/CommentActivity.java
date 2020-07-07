package com.example.emoji.community.comment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emoji.MyApplication;
import com.example.emoji.R;
import com.example.emoji.base.BaseBindingActivity;
import com.example.emoji.community.UploadViewModel;
import com.example.emoji.community.upload.UploadEmojiAdapter;
import com.example.emoji.data.bmob.Comment;
import com.example.emoji.data.bmob.MyUser;
import com.example.emoji.data.bmob.Post;
import com.example.emoji.databinding.ActivityCommentBinding;
import com.example.emoji.listener.CustomClickListener;
import com.example.emoji.utils.ToastUtil;
import com.example.media.bean.Image;
import com.example.media.imageselect.CustomItemDecoration;
import com.example.media.imageselect.images.ImageSelectActivity;
import com.example.media.utils.ImageSelector;
import com.example.media.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class CommentActivity extends BaseBindingActivity<ActivityCommentBinding, CommentViewModel> {

    private Post post;
    private CommentAdapter adapter;
    private UploadViewModel uploadViewModel;
    private List<Image> allImages = new ArrayList<>(); //将要评论的图片
    private List<String> urls = new ArrayList<>(); //上传图片返回的url
    private String content; //评论的内容
    private float mProgressStatus = 0f; //进度条

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
            String objectId = bundle.getString("id");
            post.setObjectId(objectId);
        }

    }

    @Override
    public void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CommentViewModel.class);
        uploadViewModel = new ViewModelProvider(this).get(UploadViewModel.class);
    }

    @Override
    protected void initView() {
        initRecyclerView();
        onClick();
    }

    private void initRecyclerView() {
        adapter = new CommentAdapter(this);
        mBinding.rvComment.setLayoutManager(new LinearLayoutManager(this));
        CustomItemDecoration itemDecoration = new CustomItemDecoration();
        itemDecoration.setDividerColor(ContextCompat.getColor(MyApplication.getInstance(), R.color.itemDecoration));
        itemDecoration.setDividerHeight(12);
        mBinding.rvComment.addItemDecoration(itemDecoration);
        mBinding.rvComment.setAdapter(adapter);
    }

    private void onClick() {
        mBinding.ivBack.setOnClickListener(v -> {
            this.finish();
        });

        mBinding.ivSelect.setOnClickListener(v -> {
            Log.d(TAG, "----onClick: " + allImages.size());
            if (allImages.size() < 9) {
                ImageSelectActivity.startActivity(this, 1, 9 - allImages.size());
            } else {
                ToastUtil.toastShort("最多只能选择9张图片!!!");
            }
        });

        mBinding.tvSend.setOnClickListener(new CustomClickListener() {
            @Override
            protected void onSingleClick(View view) {
                content = mBinding.etComment.getText().toString();
                if (StringUtils.isEmpty(content) && allImages.size() == 0) {
                    ToastUtil.toastShort("评论的内容不能为空!!!");
                } else {
                    if (allImages.size() > 0) {
                        mBinding.rlProgress.setVisibility(View.VISIBLE);
                        for (Image image : allImages) {
                            uploadViewModel.uploadFiles(uploadViewModel.geStorageReference(), image.getPath());
                        }
                    } else {
                        saveComment(null);
                    }
                }
                mBinding.rvEmoji.setVisibility(View.GONE);
                mBinding.etComment.clearFocus();
                mBinding.etComment.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mBinding.etComment.getWindowToken(), 0);
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
                mBinding.rlProgress.setVisibility(View.GONE);
            }
        });

        //所有需要上传的图片
        uploadViewModel.getUpLoadImagesLiveData().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(List<Image> images) {
                allImages = images;
                if (images.size() == 0) {
                    mBinding.rvEmoji.setVisibility(View.GONE);
                }
            }
        });

        uploadViewModel.getUrlLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                urls.add(s);
                mProgressStatus = urls.size() * 100 / allImages.size();
                mBinding.tvProgress.setText("正在上传: " + mProgressStatus + "%");
                mBinding.progress.setProgress((int) mProgressStatus);
                if (urls.size() == allImages.size()) {
                    saveComment(urls);
                }
            }
        });
    }

    //发表评论
    private void saveComment(List<String> urls) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        final Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setPost(post);
        if (urls != null) {
            comment.setImages(urls);
        }
        viewModel.saveComment(comment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1) {
                List<Image> images = data.getParcelableArrayListExtra(ImageSelector.IMAGE_SELECTED);
                if (images != null) {
                    allImages.addAll(images);

                    show(allImages);
                    uploadViewModel.getUpLoadImagesLiveData().postValue(allImages);
                }
            }
        }
    }

    private void show(List<Image> images) {
        mBinding.rvEmoji.setVisibility(View.VISIBLE);
        mBinding.rvEmoji.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        UploadEmojiAdapter emojiAdapter = new UploadEmojiAdapter(this);
        emojiAdapter.setData(images);
        emojiAdapter.setViewModel(uploadViewModel);
        mBinding.rvEmoji.setAdapter(emojiAdapter);
    }
}