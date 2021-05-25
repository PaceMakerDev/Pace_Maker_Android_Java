package com.example.pacemaker.auth.textwatchers;

import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.ui.signup.SignUpFragment;
import com.example.pacemaker.auth.enums.SignUpInputs;
import com.example.pacemaker.util.AuthValidator;


public class EmailTextWatcher implements TextWatcher {
    private final TextView errorText;
    private final Resources resources;
    private final SignUpFragment fragment;

    public EmailTextWatcher(TextView errorText, Resources resources, SignUpFragment fragment ) {
        super();
        this.errorText = errorText;
        this.resources = resources;
        this.fragment = fragment;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (AuthValidator.isEmailValid(charSequence.toString())) {
            errorText.setVisibility(View.GONE);
            fragment.setValidation(SignUpInputs.EMAIL, true);
        } else {
            errorText.setText(resources.getText(R.string.auth_error_regex_email));
            errorText.setVisibility(View.VISIBLE);
            fragment.setValidation(SignUpInputs.EMAIL, false);
        }
        fragment.updateSignUpButton();
    }

    @Override
    public void afterTextChanged(Editable editable) { }
}
