package com.example.emoji.community.comment;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.emoji.data.bmob.Comment;
import com.example.emoji.data.bmob.Post;

import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


/**
 * 创建时间:2020/6/25
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class CommentViewModel extends ViewModel {

    private static final String TAG = "CommentViewModel";
    private MutableLiveData<List<Comment>> comments;

    public MutableLiveData<List<Comment>> getComments() {
        if (comments == null){
            comments = new MutableLiveData<>();
        }
        return comments;
    }

    //发表评论
    public void saveComment(Comment comment) {
//        MyUser user = BmobUser.getCurrentUser(MyUser.class);
//        Post post = new Post();
//        post.setObjectId("ESIt3334");
//        final Comment comment = new Comment();
//        comment.setComment(content);
//        comment.setPost(post);
//        comment.setUser(user);
        comment.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "----done: " + "评论发表成功");
                    getComment(comment.getPost());
                } else {
                    Log.d(TAG, "----done: 失败: " + e.getMessage());
                }
            }

        });

    }

    //查询帖子对应的评论
    public void getComment(Post post) {
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
//        Post post = new Post();
//        post.setObjectId("ESIt3334");
        query.addWhereEqualTo("post", new BmobPointer(post));
        //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("user,post.author");
        query.findObjects(new FindListener<Comment>() {

            @Override
            public void done(List<Comment> data, BmobException e) {
                Collections.reverse(data);
                comments.postValue(data);
            }
        });
    }
}
