package com.example.pacemaker.study.ui.studysearch;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.pacemaker.auth.enums.SignUpInputs;
import com.example.pacemaker.auth.ui.signup.SignUpFragment;

public class InputTextWatcher implements TextWatcher {
    private String tag;
    private  StudyCreateMediator mediator;

    public InputTextWatcher(StudyCreateMediator mediator, String tag) {
        super();
        this.tag = tag;
        this.mediator = mediator;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().isEmpty()) {
            mediator.requestUpdateButton(tag, false);
        } else {
            mediator.requestUpdateButton(tag, true);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
