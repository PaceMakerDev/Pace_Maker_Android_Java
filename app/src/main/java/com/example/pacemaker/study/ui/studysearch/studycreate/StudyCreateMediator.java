package com.example.pacemaker.study.ui.studysearch.studycreate;

import android.widget.TextView;

public class StudyCreateMediator {
    StudyCreateFragment studyCreateFragment;

    public void setStudyCreateFragment(StudyCreateFragment studyCreateFragment) {
        this.studyCreateFragment = studyCreateFragment;
    }

    public void setAlarmTime(String dayName, TextView amView, TextView pmView, TextView timeView, boolean isAM, String hour, String time) {
        StudyCreateFragment fragment = ((StudyCreateFragment)studyCreateFragment);
        if (isAM) {
            fragment.selectAM(dayName, amView, pmView, timeView, hour, time);
        }
        else {
            fragment.selectPM(dayName, amView, pmView, timeView, hour, time);
        }
    }

    public void requestUpdateButton(String tag, boolean result) {
        studyCreateFragment.setValidation(tag, result);
        studyCreateFragment.updateCreateButton();
    }

    public void requestUpdateAlarmDetail(String dayName, boolean result) {
        studyCreateFragment.setAlarmValidation(dayName, result);
        studyCreateFragment.updateCreateButton();
    }
}
