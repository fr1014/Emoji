package com.example.emoji.community;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.emoji.utils.ToastUtil;
import com.example.media.bean.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * 创建时间:2020/6/29
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class UploadViewModel extends ViewModel {
    private MutableLiveData<List<Image>> upLoadImagesLiveData;
    private MutableLiveData<String> url;
    private StorageReference storageRef;

    //创建FirebaseStorage引用
    public StorageReference geStorageReference() {
        if (storageRef == null){
            FirebaseStorage storage = FirebaseStorage.getInstance();
            storageRef = storage.getReference();
        }
        return storageRef;
    }

    public MutableLiveData<List<Image>> getUpLoadImagesLiveData() {
        if (upLoadImagesLiveData == null) {
            upLoadImagesLiveData = new MutableLiveData<>();
        }
        return upLoadImagesLiveData;
    }

    public MutableLiveData<String> getUrlLiveData() {
        if (url == null) {
            url = new MutableLiveData<>();
        }
        return url;
    }

    private static final String TAG = "UploadViewModel";

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
//                ToastUtil.toastShort("上传图片成功！！！");
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

                url.postValue(uri.toString());
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
}
