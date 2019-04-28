package com.healer.dev.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import com.healer.dev.R;
import com.healer.dev.data.adapter.TopicExpandableListViewAdapter;
import com.healer.dev.data.local.DatabaseManager;
import com.healer.dev.data.models.TopicModel;
import com.healer.dev.ui.toeicstudy.ToeicStudyActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ToeicCourse extends AppCompatActivity {

    private DatabaseManager mDatabase;
    private ExpandableListView mEx;
    private TopicExpandableListViewAdapter mTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toeic_score_board);

        mDatabase = DatabaseManager.getInstance(this);
        mEx = findViewById(R.id.elv_toeic);
        mTopic = new TopicExpandableListViewAdapter(this, mDatabase.getListCategory(), mDatabase.getHashMap(mDatabase.getListTopic(), mDatabase.getListCategory()));
        mEx.setAdapter(mTopic);

        mEx.setOnChildClickListener((expandableListView, view, group, child, l) -> {

            TopicModel topicModel = mDatabase.getListTopic().get(group * 5 + child);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String lastTime = simpleDateFormat.format(Calendar.getInstance().getTime());
            DatabaseManager.getInstance(ToeicCourse.this).updateLastTime(topicModel, lastTime);

            Intent intent = new Intent(ToeicCourse.this, ToeicStudyActivity.class);
            intent.putExtra("topic", topicModel);
            startActivity(intent);

            return true;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTopic.refreshList(this);
    }
}
