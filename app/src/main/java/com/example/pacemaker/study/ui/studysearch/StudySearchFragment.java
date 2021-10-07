package com.example.pacemaker.study.ui.studysearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.MainFragment;
import com.example.pacemaker.auth.ui.findinfos.FindEmailFragment;
import com.example.pacemaker.auth.ui.findinfos.FindPasswordFragment;
import com.example.pacemaker.auth.ui.login.ChangePasswordFragment;
import com.example.pacemaker.auth.ui.login.LoginFragment;
import com.example.pacemaker.auth.ui.signup.SignUpFragment;
import com.example.pacemaker.auth.ui.signup.SignUpSuccessFragment;
import com.example.pacemaker.study.StudyActivity;
import com.example.pacemaker.study.enums.FragmentTypes;
import com.example.pacemaker.study.ui.studysearch.models.NewStudy;
import com.example.pacemaker.study.ui.studysearch.models.RecommendStudy;

import java.util.ArrayList;

public class StudySearchFragment extends Fragment {
    private NewStudyRecyclerViewAdapter newStudyRecyclerViewAdapter;
    private RecommendStudyRecyclerViewAdapter recommendStudyRecyclerViewAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.study_fragment_study_search, container, false);

        ImageView view = rootView.findViewById(R.id.btn_create_study);
        view.setOnClickListener(view1 -> {
            ((StudyActivity)requireActivity()).setFragment(FragmentTypes.STUDY_CREATE);
        });
        setUpInitialRequests();
        setRecyclerView(rootView);
        return rootView;
    }

    private void setUpInitialRequests() {
        ((StudyActivity)requireActivity()).requestGetRecentStudy(requireContext());
        ((StudyActivity)requireActivity()).requestGetRecommendStudy(requireContext());
    }

    private void setRecyclerView(View view) {
        RecyclerView newStudyRecyclerView = view.findViewById(R.id.recyclerview_new_studyroom);
        newStudyRecyclerViewAdapter = new NewStudyRecyclerViewAdapter();
        newStudyRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        newStudyRecyclerView.addItemDecoration(new NewStudyViewDecoration(30));
        newStudyRecyclerView.setAdapter(newStudyRecyclerViewAdapter);
        RecyclerView recommendStudyRecyclerView = view.findViewById(R.id.recyclerview_recommend_studyroom);
        recommendStudyRecyclerViewAdapter = new RecommendStudyRecyclerViewAdapter();
        recommendStudyRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recommendStudyRecyclerView.addItemDecoration(new RecommentStudyViewDecoration(30));
        recommendStudyRecyclerView.setAdapter(recommendStudyRecyclerViewAdapter);
    }

    public void setNewStudy(ArrayList<NewStudy> studyList) {
        newStudyRecyclerViewAdapter.setStudy(studyList);
        newStudyRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void setRecommendStudy(ArrayList<RecommendStudy> studyList) {
        recommendStudyRecyclerViewAdapter.setStudy(studyList);
        recommendStudyRecyclerViewAdapter.notifyDataSetChanged();
    }

}
