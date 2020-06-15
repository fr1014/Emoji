package com.example.emoji.data.bmob;

import cn.bmob.v3.BmobObject;

/**
 * 创建时间:2020/6/7
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
class Comment extends BmobObject {
    private String comment; //评论内容
    private MyUser user;////评论的用户，Pointer类型，一对一关系
    private Post post; //所评论的帖子，这里体现的是一对多的关系，一个评论只能属于一个帖子

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
