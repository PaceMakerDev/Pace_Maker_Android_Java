package com.example.pacemaker.study.ui.studysearch.studycreate;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

public class MemberCountFilter implements InputFilter {
    private int min, max;
    private String tag;
    private StudyCreateMediator mediator;

    public MemberCountFilter(int min, int max, StudyCreateMediator mediator, String tag) {
        this.min = min;
        this.max = max;
        this.mediator = mediator;
        this.tag = tag;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        try {
            int input = Integer.parseInt(dest.toString () + source.toString());
            Log.d("MyStudy", "input filter input : " + input);
            if (isValid(input)) {
                mediator.requestUpdateButton(tag, true);
                return null;
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        mediator.requestUpdateButton(tag, false);
        return "";
    }

    private boolean isValid(int input) {
        return input >= min && input < max;
    }
}
