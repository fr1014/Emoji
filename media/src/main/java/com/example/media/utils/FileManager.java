package com.example.media.utils;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.example.media.bean.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.example.media.utils.FileUtils.checkImgExists;
import static com.example.media.utils.FileUtils.getExtensionName;

/**
 * 创建时间：2020/2/9
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class FileManager {

    //从SD卡加载图片
    public static void loadImageFromSDCard(final Context context, final DataCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //扫描图片
                Uri imgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                List<Image> images = new ArrayList<>();
                HashMap<String, List<Image>> folders = new HashMap<>();//所有图片
                ContentResolver mContentResolver = context.getContentResolver();
                /*
                 * uri使用content：//方案的URI，用于检索的内容。
                 * projection:返回哪些列的列表。传递null将返回所有列
                 * selection:声明返回哪些行的过滤器，格式为* SQL WHERE子句（不包括WHERE本身）。传递null将*返回给定URI的所有行。
                 * selectionArgs:在选择中包括？s，然后将?替换为selectionArgs中的值。这些值将绑定为字符串。
                 * sortOrder如何对行进行排序，格式为SQL ORDER BY 子句（不包括ORDER BY本身）。传递null将使用默认排序顺序，该顺序可能是无序的。
                 */
                Cursor c = mContentResolver.query(imgUri,
                        new String[]{
                                MediaStore.Images.Media.DATA,
                                MediaStore.Images.Media.DISPLAY_NAME,
                                MediaStore.Images.Media.DATE_ADDED,
                                MediaStore.Images.Media._ID,
                                MediaStore.Images.Media.MIME_TYPE
                        },
                        null,
                        null,
                        MediaStore.Images.Media.DATE_ADDED);
                if (c != null) {
                    while (c.moveToNext()) {
                        //获取图片路径
                        String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                        //获取照片名称
                        String name = c.getString(c.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                        //获取照片时间
                        long time = c.getLong(c.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                        //获取图片类型
                        String mimeType = c.getString(c.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));

                        String folderName = FileUtils.getFolderName(path);    //文件夹名称

                        //过滤未下载完成或者不存在的文件
                        if (!"downloading".equals(getExtensionName(path)) && checkImgExists(path)) {
                            //用于展示相册初始化界面
                            images.add(new Image(path, time, name, mimeType));

                            if (folders.containsKey(folderName)) {
                                List<Image> data = folders.get(folderName);
                                data.add(new Image(path, time, name, mimeType));
                            } else {
                                List<Image> data = new ArrayList<>();
                                data.add(new Image(path, time, name, mimeType));
                                folders.put(folderName, data);
                            }
                        }

                    }
                    c.close();
                }
                Collections.reverse(images);
                callBack.onSuccess(images, folders);
            }
        }).start();
    }

    private static final String TAG = "FileManager";

//    /**
//     * 把图片按文件夹拆分，第一个文件夹保存所有的图片
//     *
//     * @param images 图片集合
//     * @return 将全部图片拆分到文件夹中
//     */
//    private static ArrayList<ImgFolder> splitFolder(ArrayList<Image> images) {
//        ArrayList<ImgFolder> folders = new ArrayList<>();
//        folders.add(new ImgFolder("全部图片", images));
//        if (images != null && !images.isEmpty()) {
//            for (int i = 0; i < images.size(); i++) {
//                String path = images.get(i).getPath();      //图片路径
//                String folderName = FileUtils.getFolderName(path);    //文件夹名称
//                if (CommonUtils.isNotEmptyString(folderName)) {
//                    ImgFolder folder = getImgFolder(folderName, folders);
//                    folder.addImage(images.get(i));
//                }
//            }
//        }
//        return folders;
//    }
//
//    /**
//     * 根据通过getFolderName(path)获取到的文件夹名称来创建文件夹
//     *
//     * @param folderName getFolderName(path)获取的文件夹名称
//     * @param folders    已经创建的文件夹
//     * @return 当前文件夹的名称
//     */
//    private static ImgFolder getImgFolder(String folderName, ArrayList<ImgFolder> folders) {
//        if (!folders.isEmpty()) {
//            for (int i = 0; i < folders.size(); i++) {
//                ImgFolder imgFolder = folders.get(i);
//                if (folderName.equals(imgFolder.getName())) {
//                    return imgFolder;
//                }
//            }
//        }
//        ImgFolder newFolder = new ImgFolder(folderName);
//        folders.add(newFolder);
//        return newFolder;
//    }

    public interface DataCallBack {
        //        void onSuccess(ArrayList<ImgFolder> folders);
        void onSuccess(List<Image> images, HashMap<String, List<Image>> folders);

        void onFile();
    }
}
