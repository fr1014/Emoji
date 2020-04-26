package com.example.emoji.data.room.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 创建时间:2020/4/20
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
@Entity(tableName = "folder")
public class FolderEntity implements Parcelable {
    @PrimaryKey(autoGenerate = true) //主键自增长
    private int id;

    @ColumnInfo(name = "folder_name")
    private String name;                //文件夹名称

    private byte[] image;                //文件夹封面

    @Ignore
    public FolderEntity(String name) {
        this.name = name;
    }

    public FolderEntity(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeByteArray(this.image);
    }

    protected FolderEntity(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.image = in.createByteArray();
    }

    public static final Creator<FolderEntity> CREATOR = new Creator<FolderEntity>() {
        @Override
        public FolderEntity createFromParcel(Parcel source) {
            return new FolderEntity(source);
        }

        @Override
        public FolderEntity[] newArray(int size) {
            return new FolderEntity[size];
        }
    };
}
