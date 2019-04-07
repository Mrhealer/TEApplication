package com.healer.dev.ui;

import com.healer.dev.ui.signin.SignInContract;
import com.healer.dev.ui.signin.SignInPresenter;

public class PresenterInjector {

    public static void injectSignInPresenter(SignInContract.View signInView) {
        new SignInPresenter(signInView);
    }
}
