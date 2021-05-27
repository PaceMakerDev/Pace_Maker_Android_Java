package com.example.pacemaker.study.ui.mystudy.chart;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class XValueFormatter extends ValueFormatter {
    private int month;

    public XValueFormatter(int month) {
        this.month = month;
    }
    @Override
    public String getFormattedValue(float value) {
        return month + "/" + (int)value;
    }
}
