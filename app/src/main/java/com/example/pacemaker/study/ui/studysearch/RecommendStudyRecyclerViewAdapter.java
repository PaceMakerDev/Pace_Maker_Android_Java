package com.example.pacemaker.study.ui.studysearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pacemaker.R;
import com.example.pacemaker.study.ui.mystudy.models.Study;
import com.example.pacemaker.study.ui.studysearch.models.RecommendStudy;

import java.util.ArrayList;
import java.util.Locale;

public class RecommendStudyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<RecommendStudy> studyList;

    public RecommendStudyRecyclerViewAdapter() {
        this.studyList = new ArrayList<RecommendStudy>();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = new RecommendStudyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_study_room, parent, false));
        return viewHolder;
    }

    class RecommendStudyViewHolder extends RecyclerView.ViewHolder {
        public RecommendStudyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View view = holder.itemView;
        RecommendStudy study = studyList.get(position);

        TextView textTitle = view.findViewById(R.id.study_room_name);
        textTitle.setText(study.getTitle());
        TextView tag = view.findViewById(R.id.study_room_tag);
        tag.setText(study.getTag());
        TextView textSubTitle = view.findViewById(R.id.text_study_subtitle);
        textSubTitle.setText(study.getSubTitle());
        TextView textMembers = view.findViewById(R.id.study_room_member_count);
        textMembers.setText(Integer.toString(study.getMembers()));
        TextView studyRoomRanking = view.findViewById(R.id.study_room_ranking);
        studyRoomRanking.setText(Integer.toString(study.getRanking()));
    }

    @Override
    public int getItemCount() {
        return studyList.size();
    }

    public void setStudy(ArrayList<RecommendStudy> studyList) {
        this.studyList = studyList;
    }
}
