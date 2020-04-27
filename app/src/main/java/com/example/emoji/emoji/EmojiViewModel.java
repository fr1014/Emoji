package com.example.emoji.emoji;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.emoji.MyApplication;
import com.example.emoji.data.room.dao.EmojiDao;
import com.example.emoji.data.room.entity.EmojiEntity;
import com.example.emoji.rx.RxSchedulers;
import com.example.emoji.rx.SimpleConsumer;
import com.example.media.utils.FileUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * 创建时间:2020/4/22
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class EmojiViewModel extends ViewModel {
    private EmojiDao emojiDao;
    private LiveData<List<EmojiEntity>> getAllEmojisLive;

    public EmojiViewModel(){
        emojiDao = MyApplication.getInstance().getDataBase().getEmojiDao();
    }

    public LiveData<List<EmojiEntity>> getAllEmojisLive(int fid) {
        if (getAllEmojisLive == null){
            getAllEmojisLive = emojiDao.getAllEmojisLive(fid);
        }
        return getAllEmojisLive;
    }

    public void insert(EmojiEntity emojiEntity){
        Observable.just(emojiEntity)
                .compose(RxSchedulers.applyIO())
                .subscribe(new SimpleConsumer<EmojiEntity>() {
                    @Override
                    protected void accept(EmojiEntity emojiEntity) {
                        emojiDao.insert(emojiEntity);
                    }
                });
    }

    public void delete(EmojiEntity emojiEntity){
        Observable.just(emojiEntity)
                .compose(RxSchedulers.applyIO())
                .subscribe(new SimpleConsumer<EmojiEntity>() {
                    @Override
                    protected void accept(EmojiEntity emojiEntity) {
                        emojiDao.delete(emojiEntity);
                    }
                });
    }

    public void update(EmojiEntity emojiEntity){
        Observable.just(emojiEntity)
                .compose(RxSchedulers.applyIO())
                .subscribe(new SimpleConsumer<EmojiEntity>() {
                    @Override
                    protected void accept(EmojiEntity emojiEntity) {
                        emojiDao.update(emojiEntity);
                    }
                });
    }
}
