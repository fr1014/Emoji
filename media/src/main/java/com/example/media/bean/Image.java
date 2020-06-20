package com.example.media.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 创建时间：2020/2/10
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class Image implements Parcelable {
    private String path;
    private long time;
    private String name;
    private String mimeType; //图片类型

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeLong(this.time);
        dest.writeString(this.name);
        dest.writeString(this.mimeType);
    }

    public Image() {
    }

    public Image(String path, long time, String name, String mimeType) {
        this.path = path;
        this.time = time;
        this.name = name;
        this.mimeType = mimeType;
    }

    private Image(Parcel in) {
        this.path = in.readString();
        this.time = in.readLong();
        this.name = in.readString();
        this.mimeType = in.readString();
    }

    //置空
    public static void setEmpty(Image image){
        image = null;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public String toString() {
        return "Image{" +
                "path='" + path + '\'' +
                ", time=" + time +
                ", name='" + name + '\'' +
                ", mimeType='" + mimeType + '\'' +
                '}';
    }
}
