package com.example.pacemaker.study.ui.studysearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pacemaker.R;
import com.example.pacemaker.study.ui.mystudy.models.Study;
import com.example.pacemaker.study.ui.studysearch.models.NewStudy;

import java.util.ArrayList;
import java.util.Locale;

public class NewStudyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<NewStudy> studyList;
    private StudySearchFragment fragment;

    public NewStudyRecyclerViewAdapter(StudySearchFragment fragment) {
        this.studyList = new ArrayList<NewStudy>();
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = new NewStudyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_study_room, parent, false));
        return viewHolder;
    }

    class NewStudyViewHolder extends RecyclerView.ViewHolder {
        public NewStudyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View view = holder.itemView;
        NewStudy study = studyList.get(position);

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
        TextView studyRoomRanking = view.findViewById(R.id.study_room_ranking);
        studyRoomRanking.setText(Integer.toString(study.getRanking()));
        TextView textMembers = view.findViewById(R.id.study_room_member_count);
        textMembers.setText(Integer.toString(study.getMembers()));
    }

    @Override
    public int getItemCount() {
        return studyList.size();
    }

    public void setStudy(ArrayList<NewStudy> studyList) {
        this.studyList = studyList;
    }
}
