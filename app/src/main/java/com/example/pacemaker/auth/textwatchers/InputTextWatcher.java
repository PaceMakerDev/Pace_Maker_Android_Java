package com.example.pacemaker.auth.textwatchers;

import android.text.Editable;
import android.text.TextWatcher;

public interface InputTextWatcher extends TextWatcher {
    @Override
    void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2);

    @Override
    void onTextChanged(CharSequence charSequence, int i, int i1, int i2);

    @Override
    void afterTextChanged(Editable editable);

    boolean isValid();
}
