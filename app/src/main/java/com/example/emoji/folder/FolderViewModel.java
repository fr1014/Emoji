package com.example.emoji.folder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.emoji.MyApplication;
import com.example.emoji.data.room.dao.FolderDao;
import com.example.emoji.data.room.entity.FolderEntity;
import com.example.emoji.rx.RxSchedulers;
import com.example.emoji.rx.SimpleConsumer;

import java.util.List;

import io.reactivex.Observable;

/**
 * 创建时间:2020/4/20
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class FolderViewModel extends ViewModel {
    private FolderDao folderDao;
    private LiveData<List<FolderEntity>> getAllFoldersLive;

    public FolderViewModel() {
        folderDao = MyApplication.getInstance().getDataBase().getFolderDao();
    }

    public LiveData<List<FolderEntity>> getGetAllFoldersLive() {
        if (getAllFoldersLive == null) {
            getAllFoldersLive = folderDao.getAllFoldersLive();
        }
        return getAllFoldersLive;
    }

    public void insert(FolderEntity folderEntity) {
        Observable.just(folderEntity)
                .compose(RxSchedulers.applyIO())
                .subscribe(new SimpleConsumer<FolderEntity>() {
                    @Override
                    protected void accept(FolderEntity folderEntity) {
                        folderDao.insert(folderEntity);
                    }
                });
    }

    public void delete(FolderEntity folderEntity){
        Observable.just(folderEntity)
                .compose(RxSchedulers.applyIO())
                .subscribe(new SimpleConsumer<FolderEntity>() {
                    @Override
                    protected void accept(FolderEntity folderEntity) {
                        folderDao.delete(folderEntity);
                    }
                });
    }

    public void update(FolderEntity folderEntity){
        Observable.just(folderEntity)
                .compose(RxSchedulers.applyIO())
                .subscribe(new SimpleConsumer<FolderEntity>() {
                    @Override
                    protected void accept(FolderEntity folderEntity) {
                        folderDao.update(folderEntity);
                    }
                });
    }
}
