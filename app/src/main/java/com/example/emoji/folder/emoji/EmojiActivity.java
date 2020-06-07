package com.example.emoji.folder.emoji;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.customview.MotionEventImageView;
import com.example.emoji.Constants;
import com.example.emoji.R;
import com.example.emoji.base.BaseActivity;
import com.example.emoji.data.room.entity.EmojiEntity;
import com.example.emoji.data.room.entity.FolderEntity;
import com.example.emoji.utils.shareutils.NativeShareTool;
import com.example.media.bean.Image;
import com.example.media.imageselect.images.ImageSelectActivity;
import com.example.media.utils.FileUtils;
import com.example.media.utils.GlideUtils;
import com.example.media.utils.ImageSelector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmojiActivity extends BaseActivity<EmojiViewModel> {

    private String folderName;
    private int fid;    //文件夹在数据库中的id
    private byte[] bytes;//文件夹封面图片
    private CircleImageView civ;
    private TextView tvFolder;
    private MotionEventImageView motionView;
    public static final int REQUEST_CODE = 1;
    List<Image> images = new ArrayList<>();
    private RecyclerView recyclerView;
    private EmojiAdapter adapter;
    public NativeShareTool nativeShareTool;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_emoji;
    }

    @Override
    protected void initView() {
        civ = findViewById(R.id.civ);
        tvFolder = findViewById(R.id.tv_folder);
        motionView = findViewById(R.id.iv_motion);
        recyclerView = findViewById(R.id.rv_emoji);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(adapter = new EmojiAdapter(this));

        motionView.setOnClickListener(v -> ImageSelectActivity.startActivity(EmojiActivity.this, REQUEST_CODE, 0));
    }

    @Override
    protected void initBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            FolderEntity entity = bundle.getParcelable(Constants.FOLDER_ENTITY);
            if (entity != null) {
                fid = entity.getId();
                folderName = entity.getName();
                bytes = entity.getBytes();
            }
        }
        if (bytes != null) {
            GlideUtils.load(bytes, civ);
        }

        if (folderName != null) {
            tvFolder.setText(folderName);
        }
    }

    @Override
    public void initViewModel() {
        viewModel = new ViewModelProvider(this).get(EmojiViewModel.class);
        adapter.setViewModel(viewModel);
        viewModel.getAllEmojisLive(fid).observe(this, new Observer<List<EmojiEntity>>() {
            @Override
            public void onChanged(List<EmojiEntity> emojiEntities) {
                adapter.setData(emojiEntities);
            }
        });
    }

    @Override
    public void initData() {
        nativeShareTool = NativeShareTool.getInstance(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE && data != null) {
                images = data.getParcelableArrayListExtra(ImageSelector.IMAGE_SELECTED);
                if (images != null) {
                    for (Image image : images) {
                        EmojiEntity entity = null;
                        try {
                            entity = new EmojiEntity(fid, FileUtils.file2Byte(image.getPath()));
                            viewModel.insert(entity);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
