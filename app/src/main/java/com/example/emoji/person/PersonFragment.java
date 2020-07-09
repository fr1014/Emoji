package com.example.emoji.person;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.example.emoji.R;
import com.example.emoji.base.BaseBindingFragment;
import com.example.emoji.base.BaseFragment;
import com.example.emoji.data.bmob.MyUser;
import com.example.emoji.utils.GlideUtils;
import com.example.media.bean.Image;
import com.example.media.imageselect.images.ImageSelectActivity;
import com.example.media.utils.ImageSelector;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonFragment extends BaseFragment<PersonViewModel> implements View.OnClickListener {
    private View unLogin; //未登录的页面
    private View login;   //已登录的页面
    private Button btLogin;
    private ConstraintLayout rootView;
    private View headView;
    private CircleImageView head; //头像
    private TextView userName;
    private TextView praised; //获赞
    private TextView attention; //关注
    //    private MutableLiveData<MyUser> liveData;
//    private MutableLiveData<String> picLiveData;
    private StorageReference storageRef;
    private PersonAdapter adapter;
    private RecyclerView recyclerView;

    public PersonFragment() {
    }

    public static PersonFragment getInstance() {
        return new PersonFragment();
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(PersonViewModel.class);
//        liveData = viewModel.getUserMutableLiveData();
//        picLiveData = viewModel.getStringMutableLiveData();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_person;
    }

    @Override
    protected void initView(View view) {
        rootView = view.findViewById(R.id.rootView);
        unLogin = view.findViewById(R.id.unlogin);
        login = view.findViewById(R.id.login);
        btLogin = view.findViewById(R.id.bt_login);
        recyclerView = view.findViewById(R.id.rv_person);

        headView = LayoutInflater.from(getContext()).inflate(R.layout.person_head, rootView, false);
        head = headView.findViewById(R.id.iv_head);
        userName = headView.findViewById(R.id.tv_name);

        btLogin.setOnClickListener(this);
//        Boolean status = UserStatusUtil.readLoginStatus(MyApplication.getInstance());

        head.setOnClickListener(this);

        login.setVisibility(View.INVISIBLE);
        unLogin.setVisibility(View.VISIBLE);

        String[] sTitle = {"我的帖子", "我的评论", "我的收藏", "我赞过的", "浏览历史", "帮助和反馈", "推荐给好友", "退出登录"};
        Integer[] res = {R.drawable.ic_community, R.drawable.ic_comment, R.drawable.ic_collect, R.drawable.ic_good, R.drawable.ic_history, R.drawable.ic_help, R.drawable.ic_share, R.drawable.ic_logout};
        adapter = new PersonAdapter(getContext(),getChildFragmentManager());
        adapter.setHeaderView(headView);
        adapter.setImageRes(Arrays.asList(res));
        adapter.setData(Arrays.asList(sTitle));
        adapter.setPersonViewModel(viewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private static final String TAG = "PersonFragment";

    @Override
    protected void initData() {
        //创建Firebase的引用用于文件上传
        createReference();

        viewModel.getUserMutableLiveData().observe(this, user -> {
            if (user != null) {
                login.setVisibility(View.VISIBLE);
                unLogin.setVisibility(View.INVISIBLE);
                GlideUtils.load(head, user.getHeadPicUrl());
                userName.setText(user.getUsername());
            } else {
                login.setVisibility(View.INVISIBLE);
                unLogin.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getStringMutableLiveData().observe(this, headPicUrl -> {
            MyUser bmobUser = viewModel.getUser();
            if (!bmobUser.getHeadPicUrl().equals(headPicUrl)) {
                MyUser newUser = new MyUser();
                newUser.setHeadPicUrl(headPicUrl);
                viewModel.update(bmobUser, newUser);
            }
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
            viewModel.updateUser(user);
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
                    viewModel.uploadFiles(storageRef, image.getPath());
                }
            }
        }

    }

    //创建FirebaseStorage引用
    public void createReference() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

    @Override
    public boolean onBackPressed() {
        List<Fragment> fragments = getChildFragmentManager().getFragments();

        for (Fragment fragment : fragments) {
            /*如果是自己封装的Fragment的子类  判断是否需要处理返回事件*/
            if (fragment instanceof BaseBindingFragment) {
                if (((BaseBindingFragment) fragment).onBackPressed()) {
                    /*在Fragment中处理返回事件*/
                    Log.d(TAG, "----onBackPressed: ");
                    return true;
                }
            }
        }
        return false;
    }
}