package com.example.emoji.community;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
public class CommunityViewModel extends ViewModel {

    private MutableLiveData<String> stringMutableLiveData;
    private MutableLiveData<List<Post>> queryAllPostLiveData;
    private MutableLiveData<List<Post>> queryPostLiveData;

    public MutableLiveData<String> getStringMutableLiveData() {
        if (stringMutableLiveData == null) {
            stringMutableLiveData = new MutableLiveData<>();
        }
        return stringMutableLiveData;
    }

    public MutableLiveData<List<Post>> getQueryAllPostLiveData() {
        if (queryAllPostLiveData == null) {
            queryAllPostLiveData = new MutableLiveData<>();
            //查询所有帖子
            queryAllPost();
        }
        return queryAllPostLiveData;
    }

    public MutableLiveData<List<Post>> getQueryPostLiveData() {
        if (queryPostLiveData == null) {
            queryPostLiveData = new MutableLiveData<>();
            //查询对应用户帖子
            queryPostByUser();
        }
        return queryPostLiveData;
    }

    public List<Post> getPostVale() {
        return getQueryAllPostLiveData().getValue();
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

    //查询对应用户的帖子
    private void queryPostByUser() {
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
                        queryPostLiveData.postValue(object);
                        ToastUtil.toastShort("查询成功!!!");
                    } else {
                        Log.d(TAG, "----done: " + e.toString());
                    }
                }

            });
        } else {
            ToastUtil.toastShort("请先登录!!!");
        }
    }

    //查询全部帖子
    public void queryAllPost() {
        BmobQuery<Post> query = new BmobQuery<>();
        query.order("-updatedAt");
        //包含作者信息
        query.include("author");
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> posts, BmobException e) {
                if (e == null) {
                    ToastUtil.toastShort("查询成功");
                    queryAllPostLiveData.postValue(posts);
//                    for (Post post:posts){
//                        Log.d(TAG, "----done: "+post.toString());
//                    }
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
                        for (Post post : object) {
                            Log.d(TAG, "----done: " + post.toString());
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
