package com.example.pacemaker.study.ui.mystudy.chart;

import android.content.res.Resources;

import com.example.pacemaker.R;
import com.example.pacemaker.study.ui.mystudy.models.StudyTimeData;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class ChartService {
    private BarChart chart;
    private Resources resources;

    public ChartService(BarChart chart, Resources resources) {
        this.chart = chart;
        this.resources = resources;
    }

    public void drawChartByDaily() {
        ChartByDays chartByDaily = new ChartByDays(chart);

        ArrayList<BarEntry> dataList = new ArrayList<BarEntry>();
        dataList.add(new BarEntry(0f, 5f));
        dataList.add(new BarEntry(1f, 6f));
        dataList.add(new BarEntry(2f, 7f));
        dataList.add(new BarEntry(3f, 8f));
        dataList.add(new BarEntry(4f, 9f));
        dataList.add(new BarEntry(5f, 17f));
        dataList.add(new BarEntry(6f, 15f));
        chartByDaily.setDataList(dataList);
        chartByDaily.setXMin(0f);
        chartByDaily.setYMax(17f);

        chartByDaily.setCommonAttributes(resources);
        chartByDaily.drawChart(resources);
    }

    //API 완성 시 StudyTimeData 대신 StudyService 받아오기
    private void setParameters(ChartByDays chartByDays, StudyTimeData[] studyTimeDataList) {

    }
}
