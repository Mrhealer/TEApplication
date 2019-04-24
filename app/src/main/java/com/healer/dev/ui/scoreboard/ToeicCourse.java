package com.healer.dev.ui.Course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.healer.dev.R;
import com.healer.dev.data.adapter.TopicExpandableListViewAdapter;
import com.healer.dev.data.local.DatabaseManager;
import com.healer.dev.data.models.TopicModel;
import com.healer.dev.ui.toeicstudy.ToeicStudyActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ToeicCourse extends AppCompatActivity {

    DatabaseManager db;
    ExpandableListView ex;
    TopicExpandableListViewAdapter topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toeic_score_board);

        db = DatabaseManager.getInstance(this);
        ex = findViewById(R.id.elv_toeic);
        topic = new TopicExpandableListViewAdapter(this, db.getListCategory(), db.getHashMap(db.getListTopic(), db.getListCategory()));
        ex.setAdapter(topic);

        ex.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int group, int child, long l) {

                TopicModel topicModel = db.getListTopic().get(group * 5 + child);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String lastTime = simpleDateFormat.format(Calendar.getInstance().getTime());
                DatabaseManager.getInstance(ToeicCourse.this).updateLastTime(topicModel, lastTime);

                Intent intent = new Intent(ToeicCourse.this, ToeicStudyActivity.class);
                intent.putExtra("topic", topicModel);
                startActivity(intent);

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting: {
                Intent i = new Intent(ToeicCourse.this, ToeicStudyActivity.class);
                startActivity(i);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        topic.refreshList(this);
    }
}
