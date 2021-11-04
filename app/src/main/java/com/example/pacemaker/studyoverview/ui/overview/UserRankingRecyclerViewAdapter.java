package com.example.pacemaker.studyoverview.ui.overview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pacemaker.R;
import com.example.pacemaker.study.ui.studysearch.models.NormalStudy;
import com.example.pacemaker.studyoverview.models.UserRanking;

import java.util.ArrayList;

public class UserRankingRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<UserRanking> userRankingList;

    public UserRankingRecyclerViewAdapter() {
        this.userRankingList = new ArrayList<UserRanking>();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = new UserRankingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_ranking, parent, false));
        return viewHolder;
    }

    class UserRankingViewHolder extends RecyclerView.ViewHolder {
        public UserRankingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View view = holder.itemView;
        UserRanking userRanking = userRankingList.get(position);

        TextView textNumber = view.findViewById(R.id.text_number);
        String userNumber = String.format("%02d", (position+1));
        textNumber.setText(userNumber);
        TextView textName = view.findViewById(R.id.text_name);
        textName.setText(userRanking.getName());
        TextView textRecord = view.findViewById(R.id.text_record);
        textRecord.setText(userRanking.getTime());
    }

    @Override
    public int getItemCount() {
        return userRankingList.size();
    }

    public void setUserRankingList(ArrayList<UserRanking> userRankingList) {
        this.userRankingList = userRankingList;
    }
}
