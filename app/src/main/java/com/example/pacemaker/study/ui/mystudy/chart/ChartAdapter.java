package com.example.pacemaker.study.ui.mystudy.chart;

import android.content.res.Resources;
import android.view.View;
import android.widget.LinearLayout;

import com.example.pacemaker.study.ui.mystudy.models.BarGraphData;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class ChartAdapter {
    private BarChart chart;
    private HorizontalBarChart horizontalChart;
    private LinearLayout inner_chart_layout;
    private Resources resources;

    public ChartAdapter(BarChart chart, HorizontalBarChart horizontalChart, LinearLayout inner_chart_layout, Resources resources) {
        this.chart = chart;
        this.horizontalChart = horizontalChart;
        this.inner_chart_layout = inner_chart_layout;
        this.resources = resources;
    }

    public void drawChartByTime(ArrayList<BarGraphData> graphDataList) {
        showChartByTime();
        ChartByTime chartByTime = new ChartByTime(chart);
        chartByTime.setUpData(resources, graphDataList);
        chartByTime.drawChart(resources);
    }

    public void drawChartByStudy() {
        showChartByStudy();
        ChartByStudy chartByStudy = new ChartByStudy(horizontalChart);
        chartByStudy.setUpData(resources);
        chartByStudy.drawChart(resources);
    }

    private void showChartByTime() {
        chart.setVisibility(View.VISIBLE);
        inner_chart_layout.setVisibility(View.GONE);
    }

    private void showChartByStudy() {
        chart.setVisibility(View.GONE);
        inner_chart_layout.setVisibility(View.VISIBLE);
    }
}
