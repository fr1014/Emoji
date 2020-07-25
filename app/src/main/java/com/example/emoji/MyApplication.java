package com.example.emoji;

import android.app.Application;

import com.example.emoji.data.entity.room.AppDataBase;
import com.example.emoji.rx.RxSchedulers;
import com.example.emoji.rx.SimpleConsumer;

import cn.bmob.v3.Bmob;
import io.reactivex.Observable;

/**
 * 创建时间:2020/4/20
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class MyApplication extends Application {
    private static MyApplication application;
    private AppDataBase dataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        Observable.just("")
                .compose(RxSchedulers.applyIO())
                .subscribe(new SimpleConsumer<String>() {
                    @Override
                    protected void accept(String s) {
                        dataBase = AppDataBase.getInstance(application);
                    }
                });
        //第一：默认初始化
        Bmob.initialize(this, "664058c578cd8a1d81ddd3259e68973e");
    }

    public static MyApplication getInstance() {
        return application;
    }

    public AppDataBase getDataBase() {
        return dataBase;
    }
}
