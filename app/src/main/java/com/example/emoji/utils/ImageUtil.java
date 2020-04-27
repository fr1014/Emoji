package com.example.emoji.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 创建时间:2020/4/22
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class ImageUtil {

    /**
     * @param drawable R文件
     * @return 二进制
     */
    public static byte[] imgResources(Drawable drawable) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        return out.toByteArray();
    }

    /**
     * @param bytes 二进制
     * @return Bitmap
     */
    public static Bitmap getBitmapFromByte(final byte[] bytes) {   //将二进制转化为bitmap
        return (bytes == null || bytes.length == 0)
                ? null
                : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * Bytes to output stream.
     */
    public static OutputStream bytes2OutputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            os.write(bytes);
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    public static File createFile(byte[] bytes){
//        String filename = "myfile";
//        File file = new File(filename);
//        FileOutputStream outputStream;
//        try {
//            outputStream = new FileOutputStream(file);
//            outputStream.write(bytes);
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return file;
//    }
}
