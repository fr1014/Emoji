package com.example.emoji.folder;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.view.View;

import com.customview.MotionEventImageView;
import com.example.emoji.R;
import com.example.emoji.base.BaseActivity;
import com.example.emoji.data.room.entity.FolderEntity;
import com.permission.Permission;
import com.permission.PermissionListener;
import com.permission.PermissionUtils;

import java.util.List;

public class HomeActivity extends BaseActivity<FolderViewModel> {
    private RecyclerView recyclerView;
    private FolderAdapter adapter;
    private MotionEventImageView motionView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        motionView = findViewById(R.id.iv_motion);

        recyclerView = findViewById(R.id.rv_folder);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new FolderAdapter(this);
        recyclerView.setAdapter(adapter);

        motionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new AddFolderFragment());
                motionView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void initBundle() {
        initPermission();
    }

    @Override
    public void initViewModel() {
        viewModel = new ViewModelProvider(this).get(FolderViewModel.class);

        viewModel.getGetAllFoldersLive().observe(this, folderEntities -> adapter.setData(folderEntities));
    }

    public View getMotionView(){
        return motionView;
    }

    public void addFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void initPermission() {
        Permission.with(HomeActivity.this)
                .requestCode(100)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .callBack(new PermissionListener() {
                    @Override
                    public void onPermit(int requestCode, String... permission) {

                    }

                    @Override
                    public void onCancel(int requestCode, String... permission) {
                        PermissionUtils.goSetting(HomeActivity.this);
                    }
                }).send();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Permission.onRequestPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        motionView.setVisibility(View.VISIBLE);
    }

}
