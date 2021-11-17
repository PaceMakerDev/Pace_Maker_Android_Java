package com.example.pacemaker.study.ui.mystudy;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pacemaker.R;
import com.example.pacemaker.study.ui.mystudy.models.Study;

import java.util.ArrayList;
import java.util.Locale;

public class StudyRoomRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Study> studyList;
    private MyStudyFragment fragment;

    public StudyRoomRecyclerViewAdapter(MyStudyFragment fragment) {
        this.studyList = new ArrayList<Study>();
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = new StudyRoomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study_room, parent, false));
        return viewHolder;
    }

    class StudyRoomViewHolder extends RecyclerView.ViewHolder {
        public StudyRoomViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View view = holder.itemView;
        Study study = studyList.get(position);

        view.setOnClickListener(v -> {
            fragment.showStudyRoom(study.getId());
        });

        TextView title = view.findViewById(R.id.study_room_name);
        title.setText(study.getTitle());
        TextView studyRoomRanking = view.findViewById(R.id.study_room_ranking);
        studyRoomRanking.setText(Integer.toString(study.getStudyRanking()));
        TextView userRanking = view.findViewById(R.id.study_room_my_ranking);
        userRanking.setText(Integer.toString(study.getUserRankingInStudy()));

        TextView textViewStudyTime = view.findViewById(R.id.study_room_time);
        int studyTime = study.getStudyTimeInSecond();
        int hour = studyTime/3600;
        studyTime %= 3600;
        int min = studyTime / 60;
        studyTime %= 60;
        int second = studyTime;
        String studyTimeStr = String.format(Locale.KOREA,"%02d:%02d:%02d", hour, min, second);
        textViewStudyTime.setText(studyTimeStr);
    }

    @Override
    public int getItemCount() {
        return studyList.size();
    }

    public void setStudy(ArrayList<Study> studyList) {
        this.studyList = studyList;
    }
}
