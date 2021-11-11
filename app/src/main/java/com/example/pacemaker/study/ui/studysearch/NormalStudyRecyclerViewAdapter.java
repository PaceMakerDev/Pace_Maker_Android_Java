package com.example.pacemaker.study.ui.studysearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pacemaker.R;
import com.example.pacemaker.study.ui.studysearch.models.NormalStudy;

import java.util.ArrayList;

public class NormalStudyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<NormalStudy> studyList;
    private StudySearchFragment fragment;

    public NormalStudyRecyclerViewAdapter(StudySearchFragment fragment) {
        this.studyList = new ArrayList<NormalStudy>();
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = new NormalStudyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_study_room, parent, false));
        return viewHolder;
    }

    class NormalStudyViewHolder extends RecyclerView.ViewHolder {
        public NormalStudyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View view = holder.itemView;
        NormalStudy study = studyList.get(position);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = Integer.toString(study.getId());
                fragment.requestStudyDetail(study);
            }
        });

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

    public void setStudy(ArrayList<NormalStudy> studyList) {
        this.studyList = studyList;
    }
}
