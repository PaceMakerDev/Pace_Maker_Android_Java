package com.example.pacemaker.study.ui.studysearch;

import android.widget.TextView;

import com.example.pacemaker.study.ui.studysearch.studycreate.StudyCreateFragment;

public class StudySearchMediator {
    StudySearchFragment studySearchFragment;

    public void setStudySearchFragment(StudySearchFragment studySearchFragment) {
        this.studySearchFragment = studySearchFragment;
    }

    public void requestSearchResult(String input) {
        if (!studySearchFragment.isStudySearchResultVisible())
            studySearchFragment.setStudySearchResultVisible();

        studySearchFragment.requestStudySearch(input);
    }

    public void closeStudySearchResult() {
        studySearchFragment.setStudySearchResultInvisible();
    }
}
