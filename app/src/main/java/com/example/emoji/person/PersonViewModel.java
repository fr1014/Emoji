package com.example.emoji.person;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.emoji.data.bmob.MyUser;
import com.example.emoji.utils.ToastUtil;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 创建时间:2020/6/8
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class PersonViewModel extends AndroidViewModel {

    private Application application;
    private MutableLiveData<MyUser> userMutableLiveData;
    private MutableLiveData<Boolean> booleanMutableLiveData; //是否注册成功
    private MutableLiveData<String> stringMutableLiveData; //上传头像

    public PersonViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public MutableLiveData<MyUser> getUserMutableLiveData() {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> getBooleanMutableLiveData(){
        if (booleanMutableLiveData == null){
            booleanMutableLiveData = new MutableLiveData<>();
        }
        return booleanMutableLiveData;
    }

    public MutableLiveData<String> getStringMutableLiveData(){
        if (stringMutableLiveData == null){
            stringMutableLiveData = new MutableLiveData<>();
        }
        return stringMutableLiveData;
    }

    //登录
    public void login(MyUser mu) {
        mu.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    ToastUtil.toastShort("登录成功！！");
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
//                    UserStatusUtil.editLoginStatus(getApplication(), true);
                    userMutableLiveData.postValue(getUser());
                } else {
                    ToastUtil.toastShort("登录失败！！");
                }
            }
        });

    }

    //返回当前登录用户的对象
    public MyUser getUser() {
        return BmobUser.getCurrentUser(MyUser.class);
    }

    private static final String TAG = "PersonViewModel";
    //注册
    public void register(MyUser mu){
        mu.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null){
                    ToastUtil.toastShort("注册成功！！！");
                    booleanMutableLiveData.postValue(true);
                }else {
                    if (e.getErrorCode() == 202){
                        ToastUtil.toastShort("您输入的用户名已被注册！！！");
                    }
                }
            }
        });
    }

    /**
     * 更新用户信息
     * @param bmobUser  BmobUser.getCurrentUser(application);
     * @param newUser 需要更新的新的用户对象
     */
    public void update(BmobUser bmobUser,BmobUser newUser){
        newUser.update(bmobUser.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    ToastUtil.toastShort("更新用户信息成功");
                }else{
                    ToastUtil.toastShort("更新用户信息失败:" + e.getMessage());
                    Log.d(TAG, "----done: "+e.toString());
                }
            }
        });
    }

    /**
     *
     * @param picPath 图片路径
     * 图片的url
     */
    public void uploadPic(String picPath){
        BmobFile bmobFile = new BmobFile(new File(picPath));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    //返回的上传文件的完整地址
                    stringMutableLiveData.postValue(bmobFile.getFileUrl());
                    ToastUtil.toastShort("上传头像成功");
                }else{
                    ToastUtil.toastShort("上传头像失败");
                    Log.d(TAG, "----done: "+e.getMessage());
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }

}
