package com.example.emoji.data.bmob;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * 创建时间:2020/6/7
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class Post extends BmobObject implements Parcelable {
    private String content; //帖子内容
    private MyUser author; //帖子的发布者，1对1的关系
    private List<String> images; //帖子图片

    /**
     * 一对多关系：用于存储喜欢该帖子的所有用户
     */
    private BmobRelation likes;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeSerializable(this.author);
        dest.writeStringList(this.images);
        dest.writeSerializable(this.likes);
    }

    public Post() {
    }

    protected Post(Parcel in) {
        this.content = in.readString();
        this.author = (MyUser) in.readSerializable();
        this.images = in.createStringArrayList();
        this.likes = (BmobRelation) in.readSerializable();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public String toString() {
        return "Post{" +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", images=" + images +
                ", likes=" + likes +
                '}';
    }
}
