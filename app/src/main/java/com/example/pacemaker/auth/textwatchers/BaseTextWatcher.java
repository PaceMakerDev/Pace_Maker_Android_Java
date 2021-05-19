package com.example.pacemaker.auth.textwatchers;

import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.example.pacemaker.auth.SignUpFragment;
import com.example.pacemaker.auth.enums.SignUpInputs;

public class BaseTextWatcher implements TextWatcher {
    SignUpFragment fragment;
    SignUpInputs type;

    public BaseTextWatcher(SignUpFragment fragment, SignUpInputs type) {
        super();
        this.fragment = fragment;
        this.type = type;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().isEmpty()) {
            fragment.setValidation(type, false);
        } else {
            fragment.setValidation(type, true);
        }
        fragment.updateSignUpButton();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
