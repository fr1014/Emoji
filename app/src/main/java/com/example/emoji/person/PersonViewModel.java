package com.example.emoji.person;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.emoji.data.bmob.MyUser;
import com.example.emoji.utils.ToastUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Objects;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

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

    public void updateUser(MyUser user){
        getUserMutableLiveData().setValue(user);
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
    public void update(BmobUser bmobUser,MyUser newUser){
        newUser.update(bmobUser.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    userMutableLiveData.postValue(newUser);
                    ToastUtil.toastShort("更新用户信息成功");
                }else{
                    ToastUtil.toastShort("更新用户信息失败:" + e.getMessage());
                    Log.d(TAG, "----done: "+e.toString());
                }
            }
        });
    }

//    public void includesForCreateReference(String path) {
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference();
//        StorageReference imagesRef = storageRef.child("images");
//        StorageReference spaceRef = storageRef.child(path);
//    }

    //FirebaseStorage上传文件
    public void uploadFiles(StorageReference storageRef,String path){

//        StorageReference imagesRef = storageRef.child("images");

        Uri file = Uri.fromFile(new File(path));
        StorageReference riversRef = storageRef.child("headFiles/"+Objects.requireNonNull(file.getLastPathSegment()));
        UploadTask uploadTask = riversRef.putFile(file);
//
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                ToastUtil.toastShort("上传图片失败！！！");
                Log.d(TAG, "----onFailure: "+exception.toString());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                ToastUtil.toastShort("上传图片成功！！！");
                getDownloadUrl(storageRef,path);
            }
        });

    }

    //获取上传文件的url
    public void getDownloadUrl(StorageReference storageRef,String path){
        Uri file = Uri.fromFile(new File(path));
        storageRef.child("headFiles/"+Objects.requireNonNull(file.getLastPathSegment())).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d(TAG, "----onSuccess: "+uri);
                stringMutableLiveData.postValue(uri.toString());
                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.d(TAG, "---onFailure: "+exception.toString());
            }
        });
    }
}
