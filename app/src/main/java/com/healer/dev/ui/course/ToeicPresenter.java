package com.healer.dev.ui.course;

import android.content.Context;

import com.healer.dev.data.adapter.TopicExpandableListViewAdapter;
import com.healer.dev.data.local.DatabaseManager;

public class ToeicPresenter implements ToeicScoreContract {

    // TODO : LongTV9 will implement later
    private DatabaseManager mDataBase;
    private TopicExpandableListViewAdapter mTopic;

    @Override
    public void start() {

    }

    @Override
    public void loadData(Context context) {
        mDataBase = DatabaseManager.getInstance(context);

    }
}
