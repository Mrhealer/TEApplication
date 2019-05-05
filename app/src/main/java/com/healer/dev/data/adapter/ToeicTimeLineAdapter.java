package com.healer.dev.data.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healer.dev.R;
import com.healer.dev.data.models.TopicModel;
import com.healer.dev.data.timeline.TimelineView;

import java.util.List;

public class ToeicTimeLineAdapter extends RecyclerView.Adapter<ToeicTimeLineAdapter.MyHolder> {

    private List<TopicModel> mArrayList;

    public ToeicTimeLineAdapter(List<TopicModel> mArrayList) {
        this.mArrayList = mArrayList;
    }

    private OnItemClickListener mListener;

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @NonNull
    @Override
    public ToeicTimeLineAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_timeline, viewGroup, false);

        return new MyHolder(itemView, i);
    }

    @Override
    public void onBindViewHolder(@NonNull ToeicTimeLineAdapter.MyHolder myHolder, int i) {
        TopicModel topicModel = mArrayList.get(i);
        sendData(myHolder, R.drawable.ic_tree, R.color.dot_dark5);
        myHolder.tvNameTopic.setText(topicModel.name);
        String lastName = topicModel.lastTime;
        if (lastName != null) {
            myHolder.tvLastTime.setText(lastName);
        }
    }

    private void sendData(MyHolder myHolder, int drawable, int color) {
        myHolder.timelineView.setMarker(getDrawable(myHolder.itemView.getContext(), drawable, ContextCompat.getColor(myHolder.itemView.getContext(), color)));
    }

    private Drawable getDrawable(Context context, int drawable, int color) {
        Drawable drawable1 = VectorDrawableCompat.create(context.getResources(), drawable, context.getTheme());
        if (drawable1 != null) {
            drawable1.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        return drawable1;
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    /**
     * class custom item for adapter
     */
    public class MyHolder extends RecyclerView.ViewHolder {

        TextView tvNameTopic, tvLastTime;
        TimelineView timelineView;

        public MyHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            tvNameTopic = itemView.findViewById(R.id.tv_name_topic);
            timelineView = itemView.findViewById(R.id.timeline);
            tvLastTime = itemView.findViewById(R.id.tv_last_time);
            timelineView.initLine(viewType);
            itemView.setOnClickListener(v -> mListener.onItemClick(getAdapterPosition()));
        }
    }

    /**
     * set listener click when user click item
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }

    /**
     * Interface for Listener Click
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
