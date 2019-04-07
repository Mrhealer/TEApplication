package com.healer.dev.ui.signin;

import android.net.Uri;

import com.healer.dev.ui.BasePresenter;
import com.healer.dev.ui.BaseView;

public interface SignInContract {

    /**
     * SignIn View
     */
    interface View extends BaseView<Presenter> {

        void loginSuccess();

        void loginFailure(int statusCode, String message);

        void startSignIn();

        void navigateToProfile();
    }

    /**
     * SignIn Presenter
     */
    interface Presenter extends BasePresenter {

        void handleLoginRequest();

        void handleLoginSuccess(String email, String displayName, Uri photoUrl);

        void handleLoginFailure(int statusCode, String message);
    }

}
