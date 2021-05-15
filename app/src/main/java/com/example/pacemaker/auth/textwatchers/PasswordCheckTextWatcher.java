package com.example.pacemaker.auth.textwatchers;

import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.SignUpFragment;
import com.example.pacemaker.auth.enums.SignUpInputs;
import com.example.pacemaker.util.AuthValidator;


public class PasswordCheckTextWatcher implements TextWatcher {
    private final TextView errorText;
    private final Resources resources;
    private final SignUpFragment fragment;
    private final EditText pwView;


    public PasswordCheckTextWatcher(TextView errorText, EditText pwView, Resources resource, SignUpFragment fragment) {
        super();
        this.errorText = errorText;
        this.resources = resource;
        this.pwView = pwView;
        this.fragment = fragment;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String password = pwView.getText().toString();
        if (password.compareTo(charSequence.toString()) == 0) {
            errorText.setVisibility(View.GONE);
            fragment.setValidation(SignUpInputs.PW_CHECK, true);
        } else {
            errorText.setText(resources.getText(R.string.auth_error_password_check));
            errorText.setVisibility(View.VISIBLE);
            fragment.setValidation(SignUpInputs.PW_CHECK, false);
        }
        fragment.updateSignUpButton();
    }

    @Override
    public void afterTextChanged(Editable editable) { }


}
