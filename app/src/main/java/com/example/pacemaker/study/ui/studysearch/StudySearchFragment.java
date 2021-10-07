package com.example.pacemaker.study.ui.studysearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pacemaker.R;
import com.example.pacemaker.study.StudyActivity;
import com.example.pacemaker.study.enums.FragmentTypes;
import com.example.pacemaker.study.ui.studysearch.models.NewStudy;
import com.example.pacemaker.study.ui.studysearch.models.NormalStudy;

import java.util.ArrayList;

public class StudySearchFragment extends Fragment {
    private NewStudyRecyclerViewAdapter newStudyRecyclerViewAdapter;
    private NormalStudyRecyclerViewAdapter recommendStudyRecyclerViewAdapter;
    private NormalStudyRecyclerViewAdapter studySearchRecyclerViewAdapter;
    private ConstraintLayout newRecommendStudyLayout;
    private RecyclerView studySearchResultView;
    private StudySearchMediator studySearchMediator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.study_fragment_study_search, container, false);

        ImageView view = rootView.findViewById(R.id.btn_create_study);
        view.setOnClickListener(view1 -> {
            ((StudyActivity)requireActivity()).setFragment(FragmentTypes.STUDY_CREATE);
        });
        newRecommendStudyLayout = rootView.findViewById(R.id.layout_new_recommend_study);
        studySearchResultView = rootView.findViewById(R.id.recyclerview_search_study_list);
        studySearchMediator = ((StudyActivity)requireActivity()).getStudySearchMediator();
        EditText editStudySearch = rootView.findViewById(R.id.edit_study_search);
        editStudySearch.addTextChangedListener(new SearchTextWatcher(studySearchMediator));
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
        recommendStudyRecyclerViewAdapter = new NormalStudyRecyclerViewAdapter();
        recommendStudyRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recommendStudyRecyclerView.addItemDecoration(new NormalStudyViewDecoration(30));
        recommendStudyRecyclerView.setAdapter(recommendStudyRecyclerViewAdapter);

        studySearchRecyclerViewAdapter = new NormalStudyRecyclerViewAdapter();
        studySearchResultView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        studySearchResultView.addItemDecoration(new NormalStudyViewDecoration(30));
        studySearchResultView.setAdapter(studySearchRecyclerViewAdapter);
    }

    public void setNewStudy(ArrayList<NewStudy> studyList) {
        newStudyRecyclerViewAdapter.setStudy(studyList);
        newStudyRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void setRecommendStudy(ArrayList<NormalStudy> studyList) {
        recommendStudyRecyclerViewAdapter.setStudy(studyList);
        recommendStudyRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void setStudySearch(ArrayList<NormalStudy> studyList) {
        studySearchRecyclerViewAdapter.setStudy(studyList);
        studySearchRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void setStudySearchResultVisible() {
        studySearchResultView.setVisibility(View.VISIBLE);
        newRecommendStudyLayout.setVisibility(View.GONE);
    }

    public void setStudySearchResultInvisible() {
        studySearchResultView.setVisibility(View.GONE);
        newRecommendStudyLayout.setVisibility(View.VISIBLE);
    }

    public boolean isStudySearchResultVisible() {
        if (studySearchResultView.getVisibility() == View.VISIBLE)
            return true;
        return false;
    }

    public void requestStudySearch(String input) {
        ((StudyActivity)requireActivity()).requestStudySearch(requireContext(), input);
    }

}
