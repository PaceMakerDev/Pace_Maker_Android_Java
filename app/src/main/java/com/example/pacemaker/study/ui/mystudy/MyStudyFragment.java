package com.example.pacemaker.study.ui.mystudy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;
import com.example.pacemaker.study.ui.mystudy.chart.ChartService;
import com.github.mikephil.charting.charts.BarChart;

public class MyStudyFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.study_fragment_my_study, container, false);

        BarChart chart = rootView.findViewById(R.id.chart_study_time);
        ChartService chartService = new ChartService(chart, getResources());
        chartService.drawChartByDaily();

        return rootView;
    }
}
