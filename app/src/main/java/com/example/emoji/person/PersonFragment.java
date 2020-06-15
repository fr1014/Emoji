package com.example.emoji.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.emoji.R;
import com.example.emoji.base.BaseFragment;
import com.example.emoji.data.bmob.MyUser;
import com.example.emoji.utils.GlideUtils;
import com.example.emoji.utils.ToastUtil;
import com.example.media.bean.Image;
import com.example.media.imageselect.images.ImageSelectActivity;
import com.example.media.utils.ImageSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonFragment extends BaseFragment<PersonViewModel> implements View.OnClickListener {
    private View unLogin; //未登录的页面
    private View login;   //已登录的页面
    private Button btLogin;
    private ConstraintLayout container;
    private CircleImageView head; //头像
    private TextView userName;
    private TextView praised; //获赞
    private TextView attention; //关注
    private MutableLiveData<MyUser> liveData;
    private MutableLiveData<String> picLiveData;

    public PersonFragment() {
    }

    public static PersonFragment getInstance() {
        return new PersonFragment();
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(PersonViewModel.class);
        liveData = viewModel.getUserMutableLiveData();
        picLiveData = viewModel.getStringMutableLiveData();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_person;
    }

    @Override
    protected void initView(View view) {
        unLogin = view.findViewById(R.id.unlogin);
        login = view.findViewById(R.id.login);
        btLogin = view.findViewById(R.id.bt_login);
        head = view.findViewById(R.id.iv_head);
        container = view.findViewById(R.id.container);
        userName = view.findViewById(R.id.tv_name);

        btLogin.setOnClickListener(this);
//        Boolean status = UserStatusUtil.readLoginStatus(MyApplication.getInstance());

        head.setOnClickListener(this);

        login.setVisibility(View.INVISIBLE);
        unLogin.setVisibility(View.VISIBLE);


    }

    private static final String TAG = "PersonFragment";

    @Override
    protected void initData() {

        liveData.observe(this, user -> {
            login.setVisibility(View.VISIBLE);
            unLogin.setVisibility(View.INVISIBLE);
            Log.d(TAG, "----onChanged: " + user.toString());
            GlideUtils.load(head, "");
            userName.setText(user.getUsername());
        });

        picLiveData.observe(this, headPicUrl -> {
            MyUser bmobUser = viewModel.getUser();
            MyUser newUser = new MyUser();
            newUser.setHeadPicUrl(headPicUrl);
            viewModel.update(bmobUser, newUser);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_head:
                ImageSelectActivity.startActivity(this, 1, 1);
                break;
            case R.id.bt_login:
                startActivity(this, LoginActivity.class);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "----onResume: ");

        MyUser user = viewModel.getUser();
        if (user != null) {
            liveData.postValue(user);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1 && data != null) {
                List<Image> imageList = data.getParcelableArrayListExtra(ImageSelector.IMAGE_SELECTED);
                if (imageList != null) {
                    Image image = imageList.get(0);
                    Log.d(TAG, "----onActivityResult: " + image.getPath());
                    viewModel.uploadPic(image.getPath());
                }
            }
        }

    }
}