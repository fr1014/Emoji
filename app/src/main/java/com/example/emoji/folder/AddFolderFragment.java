package com.example.emoji.folder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.emoji.Constants;
import com.example.emoji.R;
import com.example.emoji.base.BaseFragment;
import com.example.emoji.data.room.entity.FolderEntity;
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
public class AddFolderFragment extends BaseFragment<FolderViewModel> {
    private ImageView ivFolder;
    private EditText etText;
    private FolderViewModel viewModel;
    private String folderName;
    private byte[] bytes;   //文件夹封面图片
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
    protected int getLayoutRes() {
        return R.layout.fragment_add_folder;
    }

    @Override
    protected void initViewModel() {
        Activity activity = this.getActivity();
        if (activity != null) {
            viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(FolderViewModel.class);
        }
    }

    @Override
    protected void initView(View view) {
        ivFolder = view.findViewById(R.id.iv_folder);
        etText = view.findViewById(R.id.et_text);
        Button confirm = view.findViewById(R.id.confirm);
        Button update = view.findViewById(R.id.update);

        if (entity != null) {
            folderName = entity.getName();
            bytes = entity.getBytes();
            if (bytes != null) {
                GlideUtils.load(bytes, ivFolder);
            }
            etText.setText(folderName);
            update.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.INVISIBLE);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etText.clearFocus();
                //更新封面
                if (image != null) {
                    try {
                        entity.setBytes(FileUtils.file2Byte(image.getPath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                if (image != null) {            //如果选择封面图
                    try {
                        folderEntity = new FolderEntity(folderName, FileUtils.file2Byte(image.getPath()));
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

    @Override
    protected void initData() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE && data != null) {
                List<Image> images = data.getParcelableArrayListExtra(ImageSelector.IMAGE_SELECTED);
                if (images != null) {
                    image = images.get(0);
                    if (image != null) {
                        String newPath = FileUtils.copyFile2Private(Objects.requireNonNull(getContext()), image.getPath());
                        GlideUtils.load(newPath, ivFolder);
                    }
                }
            }
        }
    }

    private static final String TAG = "AddFolderFragment";
    /*重写父类的onBackPressed*/
    @Override
    public boolean onBackPressed() {
        if(getParentFragment() != null){
            getParentFragment().getChildFragmentManager().popBackStack();
            Log.d(TAG, "----onBackPressed: ");
            return true;
        }
        return false;
    }

}
