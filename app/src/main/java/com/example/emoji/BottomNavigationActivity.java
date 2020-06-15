package com.example.emoji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import com.example.emoji.community.CommunityFragment;
import com.example.emoji.folder.FolderFragment;
import com.example.emoji.person.PersonFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.permission.Permission;
import com.permission.PermissionListener;
import com.permission.PermissionUtils;

import java.util.List;

public class BottomNavigationActivity extends AppCompatActivity {
    private BottomNavigationView mNavigationView;
    private Fragment mFragments[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        mFragments = new Fragment[]{FolderFragment.getInstance(), CommunityFragment.getInstance(), PersonFragment.getInstance()};

        initView();
        initPermission();
    }

    private void initView() {
        mNavigationView =findViewById(R.id.navigation);

        mNavigationView.setOnNavigationItemSelectedListener(item -> {
            onTabItemSelected(item.getItemId());
            return true;
        });

        // 由于第一次进来没有回调onNavigationItemSelected，因此需要手动调用一下切换状态的方法
        onTabItemSelected(R.id.navigation_home);
    }

    private void onTabItemSelected(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.navigation_home:
                fragment = mFragments[0];
                break;
            case R.id.navigation_community:
                fragment = mFragments[1];
                break;
            case R.id.navigation_profile:
                fragment = mFragments[2];
                break;
        }
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
        }
    }

    private void initPermission() {
        Permission.with(BottomNavigationActivity.this)
                .requestCode(100)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .callBack(new PermissionListener() {
                    @Override
                    public void onPermit(int requestCode, String... permission) {

                    }

                    @Override
                    public void onCancel(int requestCode, String... permission) {
                        PermissionUtils.goSetting(BottomNavigationActivity.this);
                    }
                }).send();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Permission.onRequestPermissionResult(requestCode, permissions, grantResults);
    }

    private static final String TAG = "BottomNavigationActivit";
    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        for (Fragment fragment : fragments) {
            /*如果是自己封装的Fragment的子类  判断是否需要处理返回事件*/
            if (fragment instanceof com.example.emoji.base.BaseFragment) {
                if (((com.example.emoji.base.BaseFragment) fragment).onBackPressed()) {
                    /*在Fragment中处理返回事件*/
                    Log.d(TAG, "----onBackPressed: ");
                    return;
                }
            }
        }
        super.onBackPressed();
    }
}