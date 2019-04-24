package com.healer.dev.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.healer.dev.data.DataHandler;
import com.healer.dev.data.DataHandlerProvider;
import com.healer.dev.data.models.Quiz;
import com.healer.dev.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements HomeContract.Presenter {

    private DataHandler mDataHandler;
    private HomeContract.View mView;

    private List<Quiz> mQuizzes;

    public HomePresenter(HomeContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerProvider.provide();
        mQuizzes = new ArrayList<>();

        //this should be the last statement
        this.mView.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {
        if (extras != null && extras.containsKey(Constants.KEY_TYPE)) {
            if (Constants.NOTIFICATION_TYPE_DISCUSSION.equalsIgnoreCase(
                    extras.getString(Constants.KEY_TYPE))) {
                String quizId = extras.getString(Constants.KEY_ACTION);
                mView.navigateToQuizDiscussion(quizId);
            } else if (Constants.NOTIFICATION_TYPE_QUIZ.equalsIgnoreCase(
                    extras.getString(Constants.KEY_TYPE))) {
                String quizId = extras.getString(Constants.KEY_ACTION);
                mView.navigateToQuizDetails(quizId);
            }
        }

        mView.showLoading();
        mDataHandler.fetchQuizzes(0, new DataHandler.Callback<List<Quiz>>() {
            @Override
            public void onResponse(List<Quiz> result) {
                mQuizzes.clear();
                mQuizzes.addAll(result);
                mView.loadQuizzes(result);
                mView.hideLoading();
            }

            @Override
            public void onError() {
                mView.onQuizLoadError();
                mView.hideLoading();
            }
        });

        // Load user image, name and slack handle in navigation drawer
        mView.loadUserImageInDrawer(mDataHandler.getUserPic());
        mView.loadSlackHandleInDrawer(mDataHandler.getSlackHandle());
        mView.loadUserNameInDrawer(mDataHandler.getUserName());
    }

    @Override
    public void destroy() {
        this.mView = null;
        this.mDataHandler = null;
    }

    @Override
    public void onQuizClicked(Quiz quiz) {
        mView.navigateToQuizDesc(quiz);
    }

    @Override
    public void onNavigationItemSelected(int navItemSpecifier) {
        switch (navItemSpecifier) {
            case HomeContract.NAVIGATION_ABOUT:
                mView.navigateToAboutScreen();
                break;
            case HomeContract.NAVIGATION_CREATE_QUIZ:
                mView.navigateToCreateQuiz();
                break;
            case HomeContract.NAVIGATION_EDIT_PROFILE:
                mView.navigateToEditProfile();
                break;
            case HomeContract.NAVIGATION_NOTIFICATIONS:
                mView.navigateToNotifications();
                break;
            case HomeContract.NAVIGATION_RESOURCES:
                mView.navigateToResources();
                break;
            case HomeContract.NAVIGATION_SETTINGS:
                mView.navigateToSettings();
                break;
            case HomeContract.NAVIGATION_COURSE:
                mView.navigateToCourse();
                break;
            default:
                break;

        }
    }

    @Override
    public void onLogoutClicked() {
        // TODO: Clear all local storage. SQLite, Shared Preferences, perform firebase logout and
        // navigate to login screen again
    }

    @Override
    public void onAllQuizSelected() {
        // Refetch the list from server when all quizzes selected
        start(null);
    }

    @Override
    public void onAttemptedQuizSelected() {
        List<Quiz> attemptedQuizzes = new ArrayList<>();
        for (Quiz quiz : mQuizzes) {
            if (quiz.isAttempted()) {
                attemptedQuizzes.add(quiz);
            }
        }
        if (attemptedQuizzes.isEmpty()) {
            mView.handleEmptyView(HomeContract.ATTEMPTED_QUIZZES);
        }
        mView.loadQuizzes(attemptedQuizzes);
    }

    @Override
    public void onUnAttemptedQuizSelected() {
        List<Quiz> unAttemptedQuizzes = new ArrayList<>();
        for (Quiz quiz : mQuizzes) {
            if (!quiz.isAttempted()) {
                unAttemptedQuizzes.add(quiz);
            }
        }
        if (unAttemptedQuizzes.isEmpty()) {
            mView.handleEmptyView(HomeContract.UNATTEMPTED_QUIZZES);
        }
        mView.loadQuizzes(unAttemptedQuizzes);
    }

    @Override
    public void onBookmarkSelected() {
        List<Quiz> bookmarkedQuizzes = new ArrayList<>();
        for (Quiz quiz : mQuizzes) {
            if (quiz.isBookmarked()) {
                bookmarkedQuizzes.add(quiz);
            }
        }
        if (bookmarkedQuizzes.isEmpty()) {
            mView.handleEmptyView(HomeContract.BOOKMARKED_QUIZZES);
        }
        mView.loadQuizzes(bookmarkedQuizzes);
    }

    @Override
    public void onBookmarkStatusChange(Quiz quiz) {
        mDataHandler.updateQuizBookmarkStatus(quiz.getKey(), quiz.isBookmarked(), new DataHandler.Callback<Void>() {
            @Override
            public void onResponse(Void result) {

            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onPartFiveSelected() {
        List<Quiz> partQuizzes = new ArrayList<>();
        for (Quiz quiz : mQuizzes) {
            if (quiz.getpart() == 5) {
                partQuizzes.add(quiz);
            }
        }
        if (partQuizzes.isEmpty()) {
            mView.handleEmptyView(HomeContract.PART_QUIZZES);
        }
        mView.loadQuizzes(partQuizzes);
    }

    @Override
    public void onPartSixSelected() {
        List<Quiz> partQuizzes = new ArrayList<>();
        for (Quiz quiz : mQuizzes) {
            if (quiz.getpart() == 6) {
                partQuizzes.add(quiz);
            }
        }
        if (partQuizzes.isEmpty()) {
            mView.handleEmptyView(HomeContract.PART_QUIZZES);
        }
        mView.loadQuizzes(partQuizzes);
    }
}