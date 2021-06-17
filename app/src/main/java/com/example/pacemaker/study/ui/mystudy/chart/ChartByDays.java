package com.example.pacemaker.study.ui.mystudy.chart;

import android.content.res.Resources;

import com.example.pacemaker.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

import lombok.Setter;

@Setter
public class ChartByDays {
    private BarChart chart;
    private float xMin, yMax;
    private ArrayList<BarEntry> dataList;

    public ChartByDays(BarChart chart) {
        this.chart  = chart;
    }

    public void setCommonAttributes(Resources resources, ValueFormatter xAxisValueFormatter) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  // x축 데이터의 위치
        xAxis.setTextSize(10f);   //텍스트 사이즈 (float으로해줘야함)
        xAxis.setTextColor(resources.getColor(R.color.axisLabel, null));
        xAxis.setDrawGridLines(false);   // 그리드 그릴지 말지
        xAxis.setGranularityEnabled(true);  //x축 간격을 제한하는 세분화 기능
        xAxis.setGranularity(1f);   // 데이터 표시 간격
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineWidth(1f);
        xAxis.setAxisLineColor(resources.getColor(R.color.white, null));
        xAxis.setValueFormatter(xAxisValueFormatter);


        YAxis yAxis = chart.getAxisLeft();
        yAxis.setDrawGridLines(true);
        yAxis.setDrawAxisLine(false);
        yAxis.setGranularity(1f);
        yAxis.setTextColor(resources.getColor(R.color.axisLabel, null));
        yAxis.setValueFormatter(new StudyYValueFormatter());

        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setVisibleXRangeMaximum(7);
        chart.setPinchZoom(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(false);
        chart.animateY(500);
        chart.setXAxisRenderer(new StudyXAxisRenderer(chart.getViewPortHandler(), chart.getXAxis(), chart.getTransformer(YAxis.AxisDependency.LEFT)));
        chart.setExtraBottomOffset(20f);
    }


    public void drawChart(Resources resources) {
        XAxis xAxis = chart.getXAxis();
        //xAxis.setAxisMinimum(xMin-0.5f);   // 데이터의 최소 표시값

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setGranularity(1f);
        yAxis.setAxisMinimum(0f);
        float gap = Math.max(1f, (int)(yMax / 10));
        yAxis.setAxisMaximum(yMax + gap);   // y축의 왼쪽 데이터 최대값
        addData(resources);
    }


    private void addData(Resources resources) {
        ArrayList<IBarDataSet> barDataSet = new ArrayList<IBarDataSet>();
        if (dataList.size() > 1) {
            BarDataSet previousDataSet = createPreviousSet(resources.getColor(R.color.previousBar, null));
            barDataSet.add(previousDataSet);
        }
        BarDataSet todayDataSet = createTodaySet(resources.getColor(R.color.todayBar, null));
        barDataSet.add(todayDataSet);

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.6f);
        chart.setData(data);
    }




    private BarDataSet createTodaySet(int barColor) {
        BarDataSet set = new BarDataSet(dataList.subList(dataList.size()-1, dataList.size()), "study time");

        set.setAxisDependency(YAxis.AxisDependency.LEFT);       //y값 데이터를 왼쪽으로
        set.setColor(barColor); //색깔 지정
        set.setValueTextSize(10f);  //글자크기
        set.setValueTextColor(barColor);
        set.setValueFormatter(new StudyYValueFormatter());
        set.setDrawValues(true);    //값을 그리기
        return set;
    }

    private BarDataSet createPreviousSet(int barColor) {
        BarDataSet set = new BarDataSet(dataList.subList(0, dataList.size()-1), "study time");    // 범례, yVals 설정(라인차트 데이터셋)

        set.setAxisDependency(YAxis.AxisDependency.LEFT);       //y값 데이터를 왼쪽으로
        set.setColor(barColor); //색깔 지정
        set.setValueTextSize(10f);  //글자크기
        set.setDrawValues(false);    //값을 그리기
        return set;
    }
}
