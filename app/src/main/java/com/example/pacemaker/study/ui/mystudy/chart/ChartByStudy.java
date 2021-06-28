package com.example.pacemaker.study.ui.mystudy.chart;

import android.content.res.Resources;
import android.graphics.Color;

import com.example.pacemaker.R;
import com.example.pacemaker.study.ui.mystudy.models.BarGraphData;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
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
public class ChartByStudy {
    private HorizontalBarChart chart;
    private float yMax;
    private ArrayList<BarEntry> dataList;

    public ChartByStudy(HorizontalBarChart chart) {
        this.chart  = chart;
    }

    public void drawChart(Resources resources) {
        ArrayList<IBarDataSet> barDataSet = new ArrayList<IBarDataSet>();
        //공부한 날짜가 하루밖에 없을 경우 고려한 케이스
        BarDataSet todayDataSet = createTodaySet(resources);
        barDataSet.add(todayDataSet);

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.35f);
        chart.setData(data);
        chart.setDrawValueAboveBar(false);
    }

    public void setUpData(Resources resources) {
        dataList = new ArrayList<BarEntry>();
        yMax = 0;
        // Test : 서버에서 받은 데이터 사용 안하고 고정된 데이터 사용중
        /*
        for (int i = 0; i < graphDataList.size(); i++) {
            //dataList.add(new BarEntry((float)i, (float)graphDataList.get(i).getYAxis()));
            BarEntry entry = new BarEntry((float)i, new float[] {1f, 2f, 3f});
            dataList.add(entry);
            yMax = Math.max(yMax, graphDataList.get(i).getYAxis());
        }

         */
        BarEntry entry = new BarEntry((float)0, new float[] {4f, 1.5f, 1f});
        yMax = 4f + 1.5f + 1f;
        dataList.add(entry);

        setCommonAttributes(resources);
    }

    private void setCommonAttributes(Resources resources) {

        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(false);
        //xAxis.setPosition(XAxis.XAxisPosition.);  // x축 데이터의 위치
        xAxis.setTextSize(10f);   //텍스트 사이즈 (float으로해줘야함)
        xAxis.setTextColor(resources.getColor(R.color.axisLabel, null));
        xAxis.setDrawGridLines(false);   // 그리드 그릴지 말지
        xAxis.setGranularityEnabled(false);  //x축 간격을 제한하는 세분화 기능
        xAxis.setGranularity(1f);   // 데이터 표시 간격
        xAxis.setDrawAxisLine(false);
        xAxis.setAxisLineWidth(1f);
        xAxis.setAxisLineColor(resources.getColor(R.color.white, null));


        YAxis yAxis = chart.getAxisLeft();
        yAxis.setEnabled(false);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawAxisLine(false);
        yAxis.setGranularity(1f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(yMax);   // y축의 왼쪽 데이터 최대값
        yAxis.setTextColor(resources.getColor(R.color.axisLabel, null));
        yAxis.setValueFormatter(new StudyYValueFormatter());



        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);

        chart.getLegend().setEnabled(false);
        chart.setVisibleXRangeMaximum(7);
        chart.setPinchZoom(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(false);
        chart.animateY(500);

        //chart.setRenderer(new ByStudyYAxisRenderer(chart, chart.getAnimator(), chart.getViewPortHandler()));
    }

    private BarDataSet createTodaySet(Resources resources) {
        BarDataSet set = new BarDataSet(dataList, "aaa");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);       //y값 데이터를 왼쪽으로

        int [] colors = new int[3];
        colors[0] = resources.getColor(R.color.firstStudy, null);
        colors[1] = resources.getColor(R.color.secondStudy, null);
        colors[2] = resources.getColor(R.color.thirdStudy, null);
        set.setColors(colors);
        set.setStackLabels(new String[] {"label1", "label2", "label3"});

        set.setValueTextSize(10f);  //글자크기
        set.setValueTextColor(Color.BLACK);
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
