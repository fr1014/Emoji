package com.example.emoji.community.upload;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.emoji.R;
import com.example.emoji.base.BaseBindingActivity;
import com.example.emoji.community.CommunityViewModel;
import com.example.emoji.databinding.ActivityCommunityBinding;
import com.example.emoji.listener.CustomClickListener;
import com.example.emoji.utils.ToastUtil;
import com.example.media.bean.Image;
import com.example.media.imageselect.images.ImageSelectActivity;
import com.example.media.utils.ImageSelector;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class CommunityActivity extends BaseBindingActivity<ActivityCommunityBinding, CommunityViewModel> implements View.OnClickListener {

    private UploadEmojiAdapter adapter;
    private ImageView ivAddEmoji;
    private List<Image> allImages = new ArrayList<>();  //将要上传的图片
    private MutableLiveData<List<Image>> upLoadImagesLiveData;
    private MutableLiveData<String> stringMutableLiveData;
    private WeakReference<CommunityActivity> activity = new WeakReference<CommunityActivity>(this);

    private StorageReference storageRef;
    //用户上传的帖子图片返回的url集合
    List<String> imagesUrl = new ArrayList<>();

    @Override
    protected ActivityCommunityBinding getViewBinding() {
        return ActivityCommunityBinding.inflate(getLayoutInflater());
    }

    private static final String TAG = "CommunityActivity";

    @Override
    public void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CommunityViewModel.class);
        upLoadImagesLiveData = viewModel.getUpLoadImagesLiveData();
        stringMutableLiveData = viewModel.getStringMutableLiveData();
    }

    @Override
    protected void initView() {
        View view = getLayoutInflater().inflate(R.layout.item_emoji, mBinding.getRoot(), false);
        ivAddEmoji = view.findViewById(R.id.iv_emoji);
        mBinding.rvEmoji.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new UploadEmojiAdapter(this);
        adapter.setData(null);
        adapter.setFooterView(view);
        adapter.setViewModel(viewModel);
        mBinding.rvEmoji.setAdapter(adapter);

        mBinding.tvUpload.setOnClickListener(this);

        ivAddEmoji.setOnClickListener(new CustomClickListener(2000L) {
            @Override
            protected void onSingleClick(View view) {
                Log.d(TAG, "----onClick: " + "开始上传");
                for (Image image : allImages) {
                    viewModel.uploadFiles(storageRef, image.getPath());
                }
            }
        });
    }

    @Override
    public void initData() {

        createReference();

        upLoadImagesLiveData.observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(List<Image> images) {
                //用户remove了图片
                if (images.size() < allImages.size()) {
                    allImages = images;
                    ivAddEmoji.setVisibility(View.VISIBLE);
                } else {
                    Log.d(TAG, "----onChanged: " + images.size());
                    if (images.size() == 9) {
                        ivAddEmoji.setVisibility(View.GONE);
                    }
                }
            }
        });

        //图片上传成功后返回的url
        stringMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("发帖成功")) {
                    activity.get().finish();
                } else {
                    imagesUrl.add(s);
                    if (imagesUrl.size() == allImages.size()) {
                        String content = mBinding.etContent.getText().toString();
                        viewModel.savePost(content, imagesUrl);
                    }
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1 && data != null) {
                List<Image> images = data.getParcelableArrayListExtra(ImageSelector.IMAGE_SELECTED);
                assert images != null;
                allImages.addAll(images);
                upLoadImagesLiveData.postValue(allImages);
                adapter.setData(images);
            }
        }
    }

    //创建FirebaseStorage引用
    public void createReference() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_emoji:
                ToastUtil.toastShort("选择要上传的图片");
                ImageSelectActivity.startActivity(activity.get(), 1, 9 - allImages.size());
                break;
        }
    }
}