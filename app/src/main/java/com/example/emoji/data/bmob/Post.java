package com.example.emoji.data.bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 创建时间:2020/6/7
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class Post extends BmobObject {
    private String content; //帖子内容
    private MyUser author; //帖子的发布者，1对1的关系
    private BmobFile image; //帖子图片

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

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }
}
