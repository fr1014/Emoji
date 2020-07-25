package com.example.emoji.data.entity.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.emoji.data.entity.room.dao.EmojiDao;
import com.example.emoji.data.entity.room.dao.FolderDao;
import com.example.emoji.data.entity.room.entity.EmojiEntity;
import com.example.emoji.data.entity.room.entity.FolderEntity;

/**
 * 创建时间:2020/4/20
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
@Database(entities = {FolderEntity.class, EmojiEntity.class},version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase INSTANCE;

    public static synchronized AppDataBase getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"app_database").build();
        }
        return INSTANCE;
    }

    public abstract FolderDao getFolderDao();

    public abstract EmojiDao getEmojiDao();
}
