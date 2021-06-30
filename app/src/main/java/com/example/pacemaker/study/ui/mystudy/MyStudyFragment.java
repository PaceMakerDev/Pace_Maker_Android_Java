package com.example.pacemaker.study.ui.mystudy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pacemaker.R;
import com.example.pacemaker.study.StudyActivity;
import com.example.pacemaker.study.ui.mystudy.chart.ChartAdapter;
import com.example.pacemaker.study.ui.mystudy.enums.GraphType;
import com.example.pacemaker.study.ui.mystudy.models.BarGraphData;
import com.example.pacemaker.study.ui.mystudy.models.Study;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;

import java.util.ArrayList;

public class MyStudyFragment extends Fragment {
    private BarChart chart;
    private HorizontalBarChart horizontalChart;
    private ChartAdapter chartAdapter;
    private ArrayList<Button> graphBtnList;
    private TextView todayRecord;
    private StudyRoomRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.study_fragment_my_study, container, false);
        setUpGraphButtons(rootView);
        chart = rootView.findViewById(R.id.chart_study_time);
        horizontalChart = rootView.findViewById(R.id.horizontal_chart_study_time);
        LinearLayout inner_chart_layout = rootView.findViewById(R.id.inner_chart_layout);

        chartAdapter = new ChartAdapter(chart, horizontalChart, inner_chart_layout, getResources());

        // API : 오늘 공부한 시간 업데이트 되면 수정하기
        todayRecord = rootView.findViewById(R.id.text_today_study_record);
        ((StudyActivity)requireActivity()).requestTodayRecord();
        changeGraph(GraphType.DAILY);

        // 삭제 시 layout에서의 id도 지우기 (R.id.temp)
        TextView tmp = rootView.findViewById(R.id.subtitle);
        tmp.setOnClickListener(view -> {
            ((StudyActivity) requireActivity()).logout();
        });

        RecyclerView studyRecyclerView = rootView.findViewById(R.id.study_list_recycler_view);
        adapter = new StudyRoomRecyclerViewAdapter();
        studyRecyclerView.setAdapter(adapter);
        studyRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        ((StudyActivity)requireActivity()).requestStudyList();

        return rootView;
    }

    public void drawChartByTime(ArrayList<BarGraphData> graphDataList) {
        chartAdapter.drawChartByTime(graphDataList);
    }

    public void drawChartByStudy( ) {
        chartAdapter.drawChartByStudy();
    }

    public void showTodayRecord(int time) {
        String record = time + ":00";
        todayRecord.setText(record);
    }

    public void showEmptyStudyList() {
        View rootView = requireView();
        LinearLayout emptyStudyListLayout = rootView.findViewById(R.id.empty_study_list_layout);
        RecyclerView studyRecyclerView = rootView.findViewById(R.id.study_list_recycler_view);
        ImageView background = rootView.findViewById(R.id.study_list_background);

        emptyStudyListLayout.setVisibility(View.VISIBLE);
        studyRecyclerView.setVisibility(View.GONE);
        background.setVisibility(View.GONE);
    }

    public void setStudyList(ArrayList<Study> studyList) {
        adapter.setStudy(studyList);
        adapter.notifyDataSetChanged();
        showStudyList();
    }

    public void showStudyList() {
        View rootView = requireView();
        LinearLayout emptyStudyListLayout = rootView.findViewById(R.id.empty_study_list_layout);
        RecyclerView studyRecyclerView = rootView.findViewById(R.id.study_list_recycler_view);
        ImageView background = rootView.findViewById(R.id.study_list_background);

        emptyStudyListLayout.setVisibility(View.GONE);
        studyRecyclerView.setVisibility(View.VISIBLE);
        background.setVisibility(View.VISIBLE);
    }



    private void setUpGraphButtons(View view) {
        graphBtnList = new ArrayList<Button>();
        Button btnDaily = view.findViewById(R.id.btn_chart_daily);
        graphBtnList.add(btnDaily);
        Button btnWeekly = view.findViewById(R.id.btn_chart_weekly);
        graphBtnList.add(btnWeekly);
        Button btnMonthly = view.findViewById(R.id.btn_chart_monthly);
        graphBtnList.add(btnMonthly);
        Button btnRoom = view.findViewById(R.id.btn_chart_room);
        graphBtnList.add(btnRoom);


        btnDaily.setOnClickListener(view1 -> {
            unselectAllGraphButtons();
            btnDaily.setTextColor(getResources().getColor(R.color.buttonChartSelected, null));
            btnDaily.setBackground(getResources().getDrawable(R.drawable.edittext_with_hint, null));
            changeGraph(GraphType.DAILY);
        });
        btnWeekly.setOnClickListener(view1 -> {
            unselectAllGraphButtons();
            btnWeekly.setTextColor(getResources().getColor(R.color.buttonChartSelected, null));
            btnWeekly.setBackground(getResources().getDrawable(R.drawable.edittext_with_hint, null));
            changeGraph(GraphType.WEEKLY);
        });
        btnMonthly.setOnClickListener(view1 -> {
            unselectAllGraphButtons();
            btnMonthly.setTextColor(getResources().getColor(R.color.buttonChartSelected, null));
            btnMonthly.setBackground(getResources().getDrawable(R.drawable.edittext_with_hint, null));
            changeGraph(GraphType.MONTHLY);
        });
        btnRoom.setOnClickListener(view1 -> {
            unselectAllGraphButtons();
            btnRoom.setTextColor(getResources().getColor(R.color.buttonChartSelected, null));
            btnRoom.setBackground(getResources().getDrawable(R.drawable.edittext_with_hint, null));
            changeGraph(GraphType.ROOM);
        });
    }

    private void unselectAllGraphButtons() {
        for (Button btn : graphBtnList) {
            btn.setTextColor(getResources().getColor(R.color.buttonChartUnselected, null));
            btn.setBackground(null);
        }
    }

    private void changeGraph(GraphType type) {
        chart.removeAllViews();
        horizontalChart.removeAllViews();
        switch (type) {
            case DAILY:
                ((StudyActivity)requireActivity()).requestDrawGraph(GraphType.DAILY);
                break;
            case WEEKLY:
                ((StudyActivity)requireActivity()).requestDrawGraph(GraphType.WEEKLY);
                break;
            case MONTHLY:
                ((StudyActivity)requireActivity()).requestDrawGraph(GraphType.MONTHLY);
                break;
            case ROOM:
                ((StudyActivity)requireActivity()).requestDrawGraph(GraphType.ROOM);
        }
    }
}
