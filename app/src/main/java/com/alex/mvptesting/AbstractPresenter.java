package com.alex.mvptesting;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class AbstractPresenter<V extends BaseView> implements BasePresenter<V> {

    private V view;

    private CompositeDisposable compositeDisposable;

    public AbstractPresenter() {
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attach(V view) {
        this.view = view;
    }

    @Override
    public V getView() {
        return view;
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void detach() {
        view = null;
        compositeDisposable.clear();
    }

    @Override
    public void addSubscription(Disposable disposable) {
        compositeDisposable.add(disposable);
    }
}