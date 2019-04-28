package com.healer.dev.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.healer.dev.R;
import com.healer.dev.data.adapter.TopicExpandableListViewAdapter;
import com.healer.dev.data.local.DatabaseManager;
import com.healer.dev.data.models.TopicModel;
import com.healer.dev.ui.toeicstudy.ToeicStudyActivity;
import com.healer.dev.utils.Connectivity;
import com.healer.dev.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ToeicCourse extends AppCompatActivity {

    private DatabaseManager mDbManager;
    private ExpandableListView mExpand;
    private TopicExpandableListViewAdapter mTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toeic_score_board);

        mDbManager = DatabaseManager.getInstance(this);
        mExpand = findViewById(R.id.elv_toeic);
        mTopic = new TopicExpandableListViewAdapter(this, mDbManager.getListCategory(),
                mDbManager.getHashMap(mDbManager.getListTopic(), mDbManager.getListCategory()));
        mExpand.setAdapter(mTopic);

        mExpand.setOnChildClickListener((expandableListView, view, group, child, l) -> {
            if (!Connectivity.isNetworkAvailable(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), getString(R.string.turn_on_internet), Toast.LENGTH_SHORT).show();
                return false;
            }
            TopicModel topicModel = mDbManager.getListTopic().get(group * 5 + child);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String lastTime = simpleDateFormat.format(Calendar.getInstance().getTime());
            DatabaseManager.getInstance(ToeicCourse.this).updateLastTime(topicModel, lastTime);

            Intent intent = new Intent(ToeicCourse.this, ToeicStudyActivity.class);
            intent.putExtra(Constants.EXTRA_STRING_DEFAULT, topicModel);
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
