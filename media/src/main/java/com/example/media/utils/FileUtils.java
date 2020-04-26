package com.example.media.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 创建时间：2020/2/10
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class FileUtils {

    /**
     * Java文件操作 获取文件扩展名
     */
    public static String getExtensionName(String filename) {
        if (CommonUtils.isNotEmptyString(filename)) {
            int dot = filename.lastIndexOf('.');
            if (dot > -1 && dot < filename.length() - 1) {
                return filename.substring(dot + 1);
            }
        }
        return "";
    }

    /**
     * 根据图片路径获取文件夹名称
     *
     * @param path 图片路径
     * @return 文件夹名称
     */
    public static String getFolderName(String path) {
        if (CommonUtils.isNotEmptyString(path)) {
            String[] strings = path.split(File.separator);
            if (strings.length >= 2) {
                return strings[strings.length - 2];
            }
        }
        return "";
    }

    /**
     *
     * @param path 文件路径
     * @return 文件名
     */
    public static String getFileName(String path) {

        int start = path.lastIndexOf("/");
        int end = path.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return path.substring(start + 1, end);
        } else {
            return null;
        }

    }

    /**
     * 判断文件是否存在
     *
     * @param path 文件的路径
     * @return
     */
    public static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 是否是图片文件
     */
    public static boolean isPicFile(String path) {
        path = path.toLowerCase();
        return path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".png");
    }

    /**
     * 检查图片是否存在。ContentResolver查询处理的数据有可能文件路径并不存在。
     *
     * @param filePath 文件路径
     * @return 该文件路径下文件是否存在
     */
    public static boolean checkImgExists(String filePath) {
        return new File(filePath).exists();
    }

    public static boolean copyFile(String oldPath$Name, String newPath$Name) {
        try {
            File oldFile = new File(oldPath$Name);

            if (!oldFile.exists() || !oldFile.isFile() || !oldFile.canRead()) {
                return false;
            }

            FileInputStream fileInputStream = new FileInputStream(oldPath$Name);
            FileOutputStream fileOutputStream = new FileOutputStream(newPath$Name);
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = fileInputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
