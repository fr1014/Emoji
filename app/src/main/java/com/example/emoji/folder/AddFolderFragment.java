package com.example.emoji.folder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.emoji.Constants;
import com.example.emoji.R;
import com.example.emoji.data.room.entity.FolderEntity;
import com.example.emoji.utils.ImageUtil;
import com.example.media.bean.Image;
import com.example.media.imageselect.images.ImageSelectActivity;
import com.example.media.utils.FileUtils;
import com.example.media.utils.GlideUtils;
import com.example.media.utils.ImageSelector;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFolderFragment extends Fragment {
    private ImageView ivFolder;
    private EditText etText;
    private Button confirm;
    private Button update;
    private FolderViewModel viewModel;
    private String folderName;
    private Bitmap bitmap;   //图片二进制文件转化
    private Image image;
    private FolderEntity entity;
    public static final int REQUEST_CODE = 1;

    public AddFolderFragment() {
    }

    public static AddFolderFragment getInstance(FolderEntity entity) {
        AddFolderFragment fragment = new AddFolderFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.FOLDER_ENTITY, entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            entity = getArguments().getParcelable(Constants.FOLDER_ENTITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_folder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivFolder = view.findViewById(R.id.iv_folder);
        etText = view.findViewById(R.id.et_text);
        confirm = view.findViewById(R.id.confirm);
        update = view.findViewById(R.id.update);

        if (entity != null) {
            bitmap = ImageUtil.getBitmapFromByte(entity.getImage());
            folderName = entity.getName();
            if (bitmap != null) {
                GlideUtils.load(bitmap, ivFolder);
            }
            etText.setText(folderName);
            update.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.INVISIBLE);
        }

        Activity activity = this.getActivity();
        if (activity != null) {
            viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(FolderViewModel.class);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etText.clearFocus();
                try {
                    //更新封面
                    if (image != null) {
                        entity.setImage(ImageUtil.imgSdCard(image.getPath()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                entity.setName(etText.getText().toString());
                viewModel.update(entity);
                Objects.requireNonNull(getActivity()).onBackPressed(); //销毁自己
            }
        });

        //新建文件夹
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etText.clearFocus();
                folderName = etText.getText().toString();
                FolderEntity folderEntity = new FolderEntity(folderName);
                if (image != null) {            //如果没有选择封面图
                    try {
                        byte[] img = ImageUtil.imgSdCard(image.getPath());
                        folderEntity = new FolderEntity(folderName, img);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                viewModel.insert(folderEntity);
                Objects.requireNonNull(getActivity()).onBackPressed(); //销毁自己
            }
        });

        ivFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelectActivity.startActivity(AddFolderFragment.this, REQUEST_CODE, 1);
            }
        });

    }

    private static final String TAG = "AddFolderFragment";

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE && data != null) {
                List<Image> images = data.getParcelableArrayListExtra(ImageSelector.IMAGE_SELECTED);
                if (images != null) {
                    image = images.get(0);
                    if (image != null) {
                        String newPath = Objects.requireNonNull(getContext()).getExternalCacheDir() + "/" + FileUtils.getFileName(image.getPath());
                        FileUtils.copyFile(image.getPath(), newPath);
                        GlideUtils.load(newPath, ivFolder);
                    }
                }
            }
        }
    }
}
