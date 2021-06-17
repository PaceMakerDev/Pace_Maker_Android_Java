package com.example.pacemaker.study.ui.mystudy.chart;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class StudyYValueFormatter extends ValueFormatter {

    @Override
    public String getFormattedValue(float value) {
        return (int)value + "h";
    }
}
