package com.example.media.imageselect.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.example.media.R;
import com.example.media.imageselect.images.ImageSelectActivity;
import com.example.media.utils.ImageSelector;

/**
 * 创建时间:2020/3/21
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public abstract class BaseActivity<VM extends ViewModel> extends AppCompatActivity {

    protected VM viewModel;

    /**
     * 启动图片选择器
     *
     * @param activity    用来启动图片选择器的activity
     * @param requestCode requestCode
     * @param maxCount    可选择图片的最大数量
     */
    public static void startActivity(Activity activity, int requestCode, int maxCount) {
        Intent intent = new Intent(activity, ImageSelectActivity.class);
        intent.putExtras(dataPackage(maxCount));
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 启动图片选择器
     *
     * @param fragment    用来启动图片选择器的fragment
     * @param requestCode requestCode
     * @param maxCount    可选择图片的最大数量
     */
    public static void startActivity(Fragment fragment, int requestCode, int maxCount) {
        Intent intent = new Intent(fragment.getActivity(), ImageSelectActivity.class);
        intent.putExtras(dataPackage(maxCount));
        fragment.startActivityForResult(intent, requestCode);
    }

    private static Bundle dataPackage(int maxCount) {
        Bundle bundle = new Bundle();
        bundle.putInt(ImageSelector.MAX_SELECT_COUNT, maxCount);
        return bundle;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        setStatusBarColor();

        initBundle();
        initView();
        initData();
    }

    protected void initToolBar(Toolbar toolbar) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //显示返回箭头
                getSupportActionBar().setDisplayShowTitleEnabled(false); //去除label
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
    }


    /**
     * 修改状态栏颜色
     */
    private void setStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.light_black));
    }

    protected void initBundle(){

    }
    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();
}
