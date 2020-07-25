package com.example.emoji.data.entity.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.emoji.data.entity.room.entity.FolderEntity;

import java.util.List;

/**
 * 创建时间:2020/4/20
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
@Dao        //数据库访问接口
public interface FolderDao {

    @Insert
    void insert(FolderEntity folderEntity);

    @Delete
    void delete(FolderEntity folderEntity);

    @Update
    void update(FolderEntity folderEntity);

    @Query("SELECT * FROM folder ORDER BY ID DESC")
    LiveData<List<FolderEntity>> getAllFoldersLive();
}
