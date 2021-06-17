package com.example.pacemaker.study.ui.mystudy.chart;

import android.content.res.Resources;
import android.util.Log;

import com.example.pacemaker.study.ui.mystudy.models.BarGraphData;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class ChartAdapter {
    private BarChart chart;
    private Resources resources;

    public ChartAdapter(BarChart chart, Resources resources) {
        this.chart = chart;
        this.resources = resources;
    }

    public void drawChartByDaily(ArrayList<BarGraphData> graphDataList) {
        ChartByDays chartByDaily = new ChartByDays(chart);

        ArrayList<BarEntry> dataList = new ArrayList<BarEntry>();
        int yMax = 0;
        for (int i = 0; i < graphDataList.size(); i++) {
            dataList.add(new BarEntry((float)i, (float)graphDataList.get(i).getYAxis()));
            yMax = Math.max(yMax, graphDataList.get(i).getYAxis());
        }
        chartByDaily.setDataList(dataList);
        chartByDaily.setXMin(0f);
        chartByDaily.setYMax((float)yMax);

        ArrayList<String> xLabelList = new ArrayList<String>();
        for (BarGraphData data : graphDataList) {
            xLabelList.add(data.getXAxis());
        }

        chartByDaily.setCommonAttributes(resources, new StudyXValueFormatter(xLabelList));
        chartByDaily.drawChart(resources);
    }
}
