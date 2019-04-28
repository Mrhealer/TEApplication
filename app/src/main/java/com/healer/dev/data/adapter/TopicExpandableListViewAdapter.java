package com.healer.dev.data.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.healer.dev.R;
import com.healer.dev.data.local.DatabaseManager;
import com.healer.dev.data.models.CategoryModel;
import com.healer.dev.data.models.TopicModel;

import java.util.HashMap;
import java.util.List;

public class TopicExpandableListViewAdapter extends BaseExpandableListAdapter {

    private String mTextDescription = "";
    private List<CategoryModel> mCategoryModelList;
    private HashMap<String, List<TopicModel>> mTopicModelHashMap;
    private Context mContext;

    public TopicExpandableListViewAdapter(Context context, List<CategoryModel> categoryModelList, HashMap<String, List<TopicModel>> topicModelHashMap) {
        this.mContext = context;
        this.mCategoryModelList = categoryModelList;
        this.mTopicModelHashMap = topicModelHashMap;
    }

    @Override
    public int getGroupCount() {
        return mCategoryModelList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mTopicModelHashMap.get(mCategoryModelList.get(i).name).size();
    }

    @Override
    public Object getGroup(int i) {
        return mCategoryModelList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mTopicModelHashMap.get(mCategoryModelList.get(i).name).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean isExpanded, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.item_list_category, viewGroup, false);

        CategoryModel categoryModel = (CategoryModel) getGroup(i);

        TextView tvCategory = view.findViewById(R.id.tv_category);
        TextView tvCategoryDes = view.findViewById(R.id.tv_category_des);
        ImageView ivArray = view.findViewById(R.id.iv_arrow);
        CardView cvCategory = view.findViewById(R.id.cv_category);

        cvCategory.setCardBackgroundColor(Color.parseColor(categoryModel.color));

        if (!isExpanded)
            ivArray.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        else
            ivArray.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);

        tvCategory.setText(categoryModel.name);

        List<TopicModel> list = mTopicModelHashMap.get(categoryModel.name);
        for (int b = 0; b < list.size(); b++) {
            mTextDescription += list.get(b).name;
            if (b != list.size() - 1)
                mTextDescription += ", ";
        }
        tvCategoryDes.setText(mTextDescription);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.item_list_topic, viewGroup, false);

        TopicModel topicModel = (TopicModel) getChild(i, i1);

        TextView tvTopic = view.findViewById(R.id.tv_name_topic);
        TextView tvLastTime = view.findViewById(R.id.tv_last_time);
        ProgressBar pbTopic = view.findViewById(R.id.pb_topic);

        pbTopic.setMax(12);
        pbTopic.setProgress(DatabaseManager.getInstance(mContext).getNumberWordById(topicModel.id, 4));
        pbTopic.setSecondaryProgress(12 - DatabaseManager.getInstance(mContext).getNumberWordById(topicModel.id, 0));

        tvTopic.setText(topicModel.name);
        if (topicModel.lastTime != null)
            tvLastTime.setText(topicModel.lastTime);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public void refreshList(Context context) {
        // Update List Expand
        mTopicModelHashMap.clear();
        DatabaseManager db = DatabaseManager.getInstance(context);
        mTopicModelHashMap.putAll(db.getHashMap(db.getListTopic(), db.getListCategory()));
        notifyDataSetChanged();
    }
}
