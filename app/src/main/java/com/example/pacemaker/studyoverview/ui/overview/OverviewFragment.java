package com.example.pacemaker.studyoverview.ui.overview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pacemaker.R;
import com.example.pacemaker.studyoverview.StudyOverviewActivity;
import com.example.pacemaker.studyoverview.models.UserRanking;

import java.util.ArrayList;


public class OverviewFragment extends Fragment {

    private UserRankingRecyclerViewAdapter userRankingViewAdapter;
    private TextView textMemberCount;
    private TextView textTitle;
    private TextView textRanking;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.studyoverview_fragment_overview, container, false);

        RecyclerView userRankingRecyclerView = rootView.findViewById(R.id.recyclerview_member_records);
        userRankingViewAdapter = new UserRankingRecyclerViewAdapter();
        userRankingRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        userRankingRecyclerView.setAdapter(userRankingViewAdapter);

        textMemberCount = rootView.findViewById(R.id.text_member_count);
        textTitle = rootView.findViewById(R.id.text_title);
        textRanking = rootView.findViewById(R.id.text_ranking);
        requestMembers();
        return rootView;
    }

    public void setUserRanking(ArrayList<UserRanking> userRankingList) {
        userRankingViewAdapter.setUserRankingList(userRankingList);
        userRankingViewAdapter.notifyDataSetChanged();
        Bundle bundle = getArguments();
        textMemberCount.setText(bundle.getString("memberCount"));
        textTitle.setText(bundle.getString("title"));
        textRanking.setText(bundle.getString("ranking"));
    }

    private void requestMembers() {
        Bundle bundle = getArguments();
        String stringId = bundle.getString("stringId");
        ((StudyOverviewActivity)requireActivity()).requestMembers(stringId);
    }
}
