package com.example.pacemaker.auth.ui.signup;

import android.widget.TextView;

import com.example.pacemaker.study.ui.studysearch.StudyCreateFragment;

public class SignUpMediator {
    SignUpFragment signUpFragment;

    public SignUpMediator(SignUpFragment signUpFragment) {
        this.signUpFragment = signUpFragment;
    }

    public void selectSubject(String name) {
        signUpFragment.selectSubject(name);
    }
}