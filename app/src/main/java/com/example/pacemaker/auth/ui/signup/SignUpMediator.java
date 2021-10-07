package com.example.pacemaker.auth.ui.signup;

public class SignUpMediator {
    SignUpFragment signUpFragment;

    public SignUpMediator(SignUpFragment signUpFragment) {
        this.signUpFragment = signUpFragment;
    }

    public void selectSubject(String name) {
        signUpFragment.selectSubject(name);
    }
}