package com.example.pacemaker.studyoverview.ui.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pacemaker.R;
import com.example.pacemaker.studyoverview.models.UserRanking;

import java.util.ArrayList;


public class OverviewFragment extends Fragment {

    private UserRankingRecyclerViewAdapter userRankingViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.studyoverview_fragment_overview, container, false);

        RecyclerView userRankingRecyclerView = rootView.findViewById(R.id.recyclerview_member_records);
        userRankingViewAdapter = new UserRankingRecyclerViewAdapter();
        userRankingRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        userRankingRecyclerView.setAdapter(userRankingViewAdapter);

        return rootView;
    }

    public void setUserRanking(ArrayList<UserRanking> userRankingList) {
        userRankingViewAdapter.setUserRankingList(userRankingList);
        userRankingViewAdapter.notifyDataSetChanged();
    }
}
