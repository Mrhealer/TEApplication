package com.healer.dev.ui;

import com.healer.dev.ui.home.HomeContract;
import com.healer.dev.ui.home.HomePresenter;
import com.healer.dev.ui.quizattempt.AttemptQuizContract;
import com.healer.dev.ui.quizattempt.AttemptQuizPresenter;
import com.healer.dev.ui.quizdetails.QuizDetailsContract;
import com.healer.dev.ui.quizdetails.QuizDetailsPresenter;
import com.healer.dev.ui.signin.SignInContract;
import com.healer.dev.ui.signin.SignInPresenter;

public class PresenterInjector {

    public static void injectSignInPresenter(SignInContract.View signInView) {
        new SignInPresenter(signInView);
    }


    public static void injectHomePresenter(HomeContract.View homeView) {
        new HomePresenter(homeView);
    }

    public static void injectQuizDetailsPresenter(QuizDetailsContract.View quizDetailsView) {
        new QuizDetailsPresenter(quizDetailsView);
    }


    public static void injectQuizAttemptPresenter(AttemptQuizContract.View view) {
        new AttemptQuizPresenter(view);
    }

}
