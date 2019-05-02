package com.healer.dev.ui.course;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.healer.dev.R;
import com.healer.dev.data.adapter.ToeicTimeLineAdapter;
import com.healer.dev.data.local.DatabaseManager;
import com.healer.dev.data.models.TopicModel;

import java.util.ArrayList;

public class ToeicActivity extends AppCompatActivity {


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
    }

    private void init() {
        mLinear = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinear);
        mAdapter = new ToeicTimeLineAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
