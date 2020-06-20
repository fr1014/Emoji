package com.example.emoji.community;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.emoji.data.bmob.MyUser;
import com.example.emoji.data.bmob.Post;
import com.example.emoji.utils.ToastUtil;
import com.example.media.bean.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 创建时间:2020/6/8
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class CommunityViewModel extends AndroidViewModel {
    private Application application;
    private MutableLiveData<List<Image>> upLoadImagesLiveData;
    private MutableLiveData<String> stringMutableLiveData;
    private MutableLiveData<List<Post>> queryAllPostLiveData;

    public CommunityViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public MutableLiveData<List<Image>> getUpLoadImagesLiveData() {
        if (upLoadImagesLiveData == null) {
            upLoadImagesLiveData = new MutableLiveData<>();
        }
        return upLoadImagesLiveData;
    }

    public MutableLiveData<String> getStringMutableLiveData() {
        if (stringMutableLiveData == null) {
            stringMutableLiveData = new MutableLiveData<>();
        }
        return stringMutableLiveData;
    }

    public MutableLiveData<List<Post>> getQueryAllPostLiveData() {
        if (queryAllPostLiveData == null){
            //查询所有帖子
            queryAllPost();
            queryAllPostLiveData = new MutableLiveData<>();
        }
        return queryAllPostLiveData;
    }

    //FirebaseStorage上传文件
    public void uploadFiles(StorageReference storageRef, String path) {

//        StorageReference imagesRef = storageRef.child("images");

        Uri file = Uri.fromFile(new File(path));
        StorageReference riversRef = storageRef.child("emojiFiles/" + Objects.requireNonNull(file.getLastPathSegment()));
        UploadTask uploadTask = riversRef.putFile(file);
//
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                ToastUtil.toastShort("上传图片失败！！！");
                Log.d(TAG, "----onFailure: " + exception.toString());
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                ToastUtil.toastShort("上传图片成功！！！");
                getDownloadUrl(storageRef, path);
            }
        });

    }

    //获取上传文件的url
    public void getDownloadUrl(StorageReference storageRef, String path) {
        Uri file = Uri.fromFile(new File(path));
        storageRef.child("emojiFiles/" + Objects.requireNonNull(file.getLastPathSegment())).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d(TAG, "----onSuccess: " + uri);

                stringMutableLiveData.postValue(uri.toString());
                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.d(TAG, "---onFailure: " + exception.toString());
            }
        });
    }

    private static final String TAG = "CommunityViewModel";

    /**
     * Bmob上传Post
     * 添加一对一关联，当前用户发布帖子
     */
    public void savePost(String content, List<String> images) {
        if (BmobUser.isLogin()) {
            Post post = new Post();
            post.setContent(content);
            //添加一对一关联，用户关联帖子
            post.setAuthor(BmobUser.getCurrentUser(MyUser.class));
            post.setImages(images);
            post.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        stringMutableLiveData.postValue("发帖成功");
                        ToastUtil.toastShort("发布帖子成功！！！");
                    } else {
                        Log.d(TAG, "----done: savePose: " + e.toString());
                    }
                }
            });
        } else {
            ToastUtil.toastShort("请先登录！！！");
        }
    }

    public void queryAllPost() {
        BmobQuery<Post> query = new BmobQuery<>();
        query.order("-updatedAt");
        //包含作者信息
        query.include("author");
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> object, BmobException e) {
                if (e == null) {
                    ToastUtil.toastShort("查询成功");
                    queryAllPostLiveData.postValue(object);
                    for (Post post:object){
                        Log.d(TAG, "----done: "+post.toString());
                    }
                } else {
                    Log.d(TAG, "----done: query:" + e.toString());
                }
            }
        });
    }

    /**
     * 查询一对一关联，查询当前用户发表的所有帖子
     */
    public void queryPostAuthor() {

        if (BmobUser.isLogin()) {
            BmobQuery<Post> query = new BmobQuery<>();
            query.addWhereEqualTo("author", BmobUser.getCurrentUser(MyUser.class));
            query.order("-updatedAt");
            //包含作者信息
            query.include("author");
            query.findObjects(new FindListener<Post>() {
                @Override
                public void done(List<Post> object, BmobException e) {
                    if (e == null) {
                        ToastUtil.toastShort("查询成功");
                        for (Post post:object){
                            Log.d(TAG, "----done: "+post.toString());
                        }
                    } else {
                        Log.d(TAG, "----done: query:" + e.toString());
                    }
                }

            });
        } else {
            ToastUtil.toastShort("请先登录！！！");
        }

    }
}
