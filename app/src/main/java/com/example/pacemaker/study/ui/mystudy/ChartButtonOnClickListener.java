package com.example.pacemaker.study.ui.mystudy;

import android.view.View;

import com.example.pacemaker.study.ui.mystudy.enums.GraphType;

public class ChartButtonOnClickListener implements View.OnClickListener{
    private GraphType type;

    public ChartButtonOnClickListener(GraphType type) {
        this.type = type;
    }
    @Override
    public void onClick(View view) {

    }
}
