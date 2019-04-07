package com.healer.dev.ui;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showLoading();

    void hideLoading();

}
