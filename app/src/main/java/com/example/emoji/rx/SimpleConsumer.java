package com.example.emoji.rx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 创建时间:2020/4/20
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public abstract class SimpleConsumer<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        accept(t);
    }

    protected abstract void accept(T t);

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
