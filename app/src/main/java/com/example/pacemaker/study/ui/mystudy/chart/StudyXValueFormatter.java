package com.example.pacemaker.study.ui.mystudy.chart;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class StudyXValueFormatter extends ValueFormatter {
    private ArrayList<String> labels;

    public StudyXValueFormatter(ArrayList<String> labels) {
        this.labels = labels;
    }
    @Override
    public String getFormattedValue(float value) {
        return labels.get((int)value);
    }
}
