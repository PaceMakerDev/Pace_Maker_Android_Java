package com.example.pacemaker.study.ui.mystudy;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pacemaker.R;
import com.example.pacemaker.study.ui.mystudy.models.Study;

import java.util.ArrayList;

public class StudyRoomRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Study> studyList;

    public StudyRoomRecyclerViewAdapter(ArrayList<Study> studyList) {
        this.studyList = studyList;
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

    }

    @Override
    public int getItemCount() {
        return studyList.size();
    }
}
