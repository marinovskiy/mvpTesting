package com.alex.mvptesting;

import io.reactivex.disposables.Disposable;

public interface BasePresenter<V> {

    void attach(V view);

    V getView();

    boolean isViewAttached();

    void detach();

    void addSubscription(Disposable disposable);

}