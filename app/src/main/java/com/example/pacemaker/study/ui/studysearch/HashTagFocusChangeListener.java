package com.example.pacemaker.study.ui.studysearch;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class HashTagFocusChangeListener implements View.OnFocusChangeListener {
    private String tag;
    private  StudyCreateMediator mediator;

    public HashTagFocusChangeListener(StudyCreateMediator mediator, String tag) {
        this.tag = tag;
        this.mediator = mediator;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText view = (EditText)v;
        String input = view.getText().toString();
        if (!hasFocus && !input.isEmpty()) {
            String [] tokens = input.split("#");
            String hashTags = "";
            for (String token : tokens) {
                token = token.trim();
                if (token.isEmpty())
                    continue;
                String hashTag = "#" + token + " ";
                hashTags = hashTags.concat(hashTag);
            }
            hashTags = hashTags.trim();
            view.setText(hashTags);
        }
        if (!input.isEmpty())
            mediator.requestUpdateButton(tag, true);
        else
            mediator.requestUpdateButton(tag, false);
    }
}
