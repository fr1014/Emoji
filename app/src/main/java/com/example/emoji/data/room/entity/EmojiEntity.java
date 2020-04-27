package com.example.emoji.data.room.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 创建时间:2020/4/22
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
/*
 * 外键可以允许你定义被引用的Entity更新时发生的行为。
 * 例如，你可以定义当删除ImageFolder时对应的Image类也被删除。
 * 可以在@ForeignKey中添加onDelete = CASCADE实现
 */
@Entity(tableName = "emoji", foreignKeys = {
        @ForeignKey(entity = FolderEntity.class,
                parentColumns = "id", childColumns = "fid",
                onDelete = ForeignKey.CASCADE)}
        , indices = {@Index(value = "fid")}
)
public class EmojiEntity implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int fid;    //所在文件夹的id

    private byte[] bytes;   //私有目录下的路径

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public EmojiEntity(int fid, byte[] bytes) {
        this.fid = fid;
        this.bytes = bytes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.fid);
        dest.writeByteArray(this.bytes);
    }

    protected EmojiEntity(Parcel in) {
        this.id = in.readInt();
        this.fid = in.readInt();
        this.bytes = in.createByteArray();
    }

    public static final Creator<EmojiEntity> CREATOR = new Creator<EmojiEntity>() {
        @Override
        public EmojiEntity createFromParcel(Parcel source) {
            return new EmojiEntity(source);
        }

        @Override
        public EmojiEntity[] newArray(int size) {
            return new EmojiEntity[size];
        }
    };
}
