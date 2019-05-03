package com.healer.dev.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.healer.dev.R;
import com.healer.dev.data.adapter.ToeicTimeLineAdapter;
import com.healer.dev.data.local.DatabaseManager;
import com.healer.dev.data.models.TopicModel;
import com.healer.dev.ui.toeicstudy.ToeicStudyActivity;
import com.healer.dev.utils.Connectivity;
import com.healer.dev.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ToeicActivity extends AppCompatActivity implements ToeicTimeLineAdapter.OnItemClickListener {


    private ArrayList<TopicModel> mArrayList;
    private ToeicTimeLineAdapter mAdapter;
    private LinearLayoutManager mLinear;
    private RecyclerView mRecyclerView;
    private DatabaseManager mDbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toeic);
        mArrayList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerView);
        mDbManager = DatabaseManager.getInstance(this);
        mArrayList = (ArrayList<TopicModel>) mDbManager.getListTopic();
        init();
        mAdapter.setOnItemClickListener(this);
    }

    private void init() {
        mLinear = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinear);
        mAdapter = new ToeicTimeLineAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(int position) {
        if (!Connectivity.isNetworkAvailable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), getString(R.string.turn_on_internet), Toast.LENGTH_SHORT).show();
            return;
        }
        TopicModel topicModel = mDbManager.getListTopic().get(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String lastTime = simpleDateFormat.format(Calendar.getInstance().getTime());
        DatabaseManager.getInstance(ToeicActivity.this).updateLastTime(topicModel, lastTime);
        Intent intent = new Intent(ToeicActivity.this, ToeicStudyActivity.class);
        intent.putExtra(Constants.EXTRA_STRING_DEFAULT, topicModel);
        startActivity(intent);
    }
}
