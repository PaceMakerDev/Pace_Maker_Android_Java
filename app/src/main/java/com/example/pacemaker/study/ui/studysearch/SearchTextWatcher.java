package com.example.pacemaker.study.ui.studysearch;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.pacemaker.study.ui.studysearch.studycreate.StudyCreateMediator;

public class SearchTextWatcher implements TextWatcher {
    private String tag;
    private StudySearchMediator mediator;

    public SearchTextWatcher(StudySearchMediator mediator) {
        super();
        this.mediator = mediator;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().isEmpty()) {
            mediator.closeStudySearchResult();
        } else {
            String input = charSequence.toString();
            mediator.requestSearchResult(input);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
