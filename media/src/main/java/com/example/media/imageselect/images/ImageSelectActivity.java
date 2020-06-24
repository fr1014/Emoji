package com.example.media.imageselect.images;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.media.R;
import com.example.media.bean.Image;
import com.example.media.imageselect.CustomItemDecoration;
import com.example.media.imageselect.SelectViewModel;
import com.example.media.imageselect.base.BaseActivity;
import com.example.media.utils.CommonUtils;
import com.example.media.utils.FileManager;
import com.example.media.utils.ImageSelector;
import com.example.media.utils.MyPopupWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ImageSelectActivity extends BaseActivity implements View.OnClickListener, FileManager.DataCallBack, FolderAdapter.SelectFolderListener, ImageAdapter.SelectImageListener {
    private ConstraintLayout mRootView;
    private Toolbar mToolbar;
    private TextView mTvFolder;     //文件夹名称
    private Button mDetermine;      //确定按钮
    private FolderAdapter mFolderAdapter;
    private RecyclerView mRvImage;
    private ImageAdapter mImageAdapter;
    private SelectViewModel viewModel;
    private PopupWindow mPopupWindow;
    private int imageCount;        //选择图片的数量,小于等于0时不限选择的数量
    private List<Image> imagesSelected = new ArrayList<>();
    private List<Image> images;
    private LinkedHashMap<String,List<Image>> allFolders = new LinkedHashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_selecter;
    }

    @Override
    protected void initView() {
        mRootView = findViewById(R.id.rootView);
        mRvImage = findViewById(R.id.rv_image);
        mToolbar = findViewById(R.id.toolbar);
        mTvFolder = findViewById(R.id.tv_folder);
        mDetermine = findViewById(R.id.bt_determine);

        initToolBar(mToolbar);

        initImageList();

        initVMLiveData();

        //扫描本地图片
        FileManager.loadImageFromSDCard(this, this);
    }

    @Override
    protected void initBundle() {
        Intent intent = getIntent();
        imageCount = intent.getIntExtra(ImageSelector.MAX_SELECT_COUNT, 0);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        super.initToolBar(toolbar);
        mTvFolder.setOnClickListener(this);
        mDetermine.setOnClickListener(this);
    }

    //展示文件夹列表 popupWindow + RecyclerView
    private void showPopupWindow(View rootView,View showAsDropDown) {
        View view = LayoutInflater.from(this).inflate(R.layout.popupwindow_folder, null);
        RecyclerView rvFolder = view.findViewById(R.id.rv_folder);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFolder.setLayoutManager(linearLayoutManager);
        //rv分割线
        CustomItemDecoration itemDecoration = new CustomItemDecoration();
        itemDecoration.setDividerColor(ContextCompat.getColor(this,R.color.dividerColor));
        rvFolder.addItemDecoration(itemDecoration);

        rvFolder.setAdapter(mFolderAdapter);

        int height = rootView.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        mPopupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, height * 4 / 5);
        MyPopupWindow.showPopupWindow(view,mPopupWindow,showAsDropDown);
    }

    //初始化viewModel + LiveData观察数据
    private void initVMLiveData() {
        //创建ViewModel对象
        viewModel = new ViewModelProvider(this).get(SelectViewModel.class);
        //创建用于更新UI的观察器
        final Observer<LinkedHashMap<String, List<Image>>> imageObserver = new Observer<LinkedHashMap<String, List<Image>>>() {

            @Override
            public void onChanged(LinkedHashMap<String, List<Image>> stringListLinkedHashMap) {
                mFolderAdapter.setImageFolders(allFolders);
                mTvFolder.setVisibility(View.VISIBLE);
                mTvFolder.setText("全部图片");    //为TextView设置文件夹名称

                mImageAdapter.setImages(images);
            }
        };
        viewModel.getImageFolders().observe(this, imageObserver);
    }

    //扫描本地图片成功
    @Override
    public void onSuccess(List<Image> images, HashMap<String, List<Image>> folders) {
        this.images = images;
        Map<String,List<Image>> map = new HashMap<>();
        map.put("全部图片",images);
        allFolders.putAll(map);
        allFolders.putAll(folders);
        viewModel.getImageFolders().postValue(allFolders);
    }

    //扫描本地图片失败
    @Override
    public void onFile() {

    }

    //初始化图片列表
    private void initImageList() {
        //初始化folders的Adapter
        mFolderAdapter = new FolderAdapter(this, this);

        mRvImage.setLayoutManager(new GridLayoutManager(this, 4));
        mImageAdapter = new ImageAdapter(this, this);
        mImageAdapter.setCount(imageCount);
        mRvImage.setAdapter(mImageAdapter);
    }

    @Override
    public void selectFolder(String name, List<Image> images) {
        MyPopupWindow.dismiss(mPopupWindow);
        mTvFolder.setText(name);
        mRvImage.scrollToPosition(0);       //RecyclerView回到起始位置
        mImageAdapter.setImages(images);
    }

    @Override
    public void selectImage(List<Image> imagesSelected) {
        this.imagesSelected = imagesSelected;
        int size = imagesSelected.size();
        if (imageCount != 0) {
            mDetermine.setText(String.format("确定(%d / %d)", size, imageCount));
        } else {
            mDetermine.setText(String.format("确定(%d)", size));
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_folder) {
            showPopupWindow(mRootView,mToolbar);
        } else if (id == R.id.bt_determine) {
            if (imagesSelected.size() == 0) {
                CommonUtils.ToastShort(this, "请选择图片");
            } else{
                setResult((ArrayList<Image>) imagesSelected);
                finish();
            }
//            else {
//                setResult(imagesSelected.get(0));
//                finish();
//            }
        }
    }

    /**
     * 返回数据给启动此Activity的活动
     *
     * @param imagesSelected 已选择的图片
     */
    private void setResult(ArrayList<Image> imagesSelected) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(ImageSelector.IMAGE_SELECTED, imagesSelected);
        setResult(RESULT_OK, intent);
    }

//    private void setResult(Image imageSelected) {
//        Intent intent = new Intent();
//        intent.putExtra(ImageSelector.IMAGE_SELECTED, imageSelected);
//        setResult(RESULT_OK, intent);
//    }
}
