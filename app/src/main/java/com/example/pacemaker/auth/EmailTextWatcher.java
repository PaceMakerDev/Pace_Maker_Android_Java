package com.example.pacemaker.auth;

import android.app.Dialog;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pacemaker.R;
import com.example.pacemaker.util.AuthValidator;


public class EmailTextWatcher implements TextWatcher {
    private final TextView errorText;
    private final Resources resources;

    public EmailTextWatcher(TextView errorText, Resources resources) {
        super();
        this.errorText = errorText;
        this.resources = resources;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (AuthValidator.isEmailValid(charSequence.toString())) {
            errorText.setVisibility(View.GONE);
        } else {
            errorText.setText(resources.getText(R.string.auth_error_regex_email));
            errorText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) { }
}
