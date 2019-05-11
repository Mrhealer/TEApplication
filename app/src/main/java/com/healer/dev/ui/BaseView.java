package com.healer.dev.ui;

/**
 * define call back for View handle.
 *
 * @param <T>
 */
public interface BaseView<T> {

    void setPresenter(T presenter);

    void showLoading();

    void hideLoading();

}
