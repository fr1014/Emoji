package com.example.emoji.data.bmob;

import cn.bmob.v3.BmobObject;

/**
 * 创建时间:2020/6/7
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class Comment extends BmobObject {
    private String content; //评论内容
    private MyUser user;////评论的用户，Pointer类型，一对一关系
    private Post post; //所评论的帖子，这里体现的是一对多的关系，一个评论只能属于一个帖子

    public Comment(){

    }

    public Comment(String content, MyUser user, Post post){
        this.content = content;
        this.user = user;
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
