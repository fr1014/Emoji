package com.example.emoji.data.entity.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.emoji.data.entity.room.entity.EmojiEntity;

import java.util.List;

/**
 * 创建时间:2020/4/22
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
@Dao
public interface EmojiDao {

    @Insert
    void insert(EmojiEntity emojiEntity);

    @Delete
    void delete(EmojiEntity emojiEntity);

    @Update
    void update(EmojiEntity emojiEntity);

    @Query("SELECT * FROM emoji WHERE FID IN(:fid) ORDER BY ID DESC")
    LiveData<List<EmojiEntity>> getAllEmojisLive(int fid);
}
