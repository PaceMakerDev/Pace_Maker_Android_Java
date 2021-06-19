package com.example.pacemaker.study.ui.mystudy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.ArrayList;

public class MyStudyFragment extends Fragment {
    private BarChart chart;
    private ChartAdapter chartAdapter;
    private ArrayList<Button> graphBtnList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.study_fragment_my_study, container, false);
        setUpGraphButtons(rootView);
        chart = rootView.findViewById(R.id.chart_study_time);
        chartAdapter = new ChartAdapter(chart, getResources());

        ((StudyActivity)requireActivity()).requestDrawGraph(GraphType.WEEKLY);

        // 삭제 시 layout에서의 id도 지우기 (R.id.temp)
        TextView tmp = rootView.findViewById(R.id.subtitle);
        tmp.setOnClickListener(view -> {
            ((StudyActivity) requireActivity()).logout();
        });

        //Test : StudyRoom test
        ArrayList<Study> studyList = new ArrayList<Study>();
        studyList.add(new Study("name"));
        studyList.add(new Study("name"));
        RecyclerView studyRecyclerView = rootView.findViewById(R.id.study_recycler_view);
        StudyRoomRecyclerViewAdapter adapter = new StudyRoomRecyclerViewAdapter(studyList);
        studyRecyclerView.setAdapter(adapter);
        studyRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        return rootView;
    }

    public void drawChart(ArrayList<BarGraphData> graphDataList) {
        chartAdapter.drawChartByDaily(graphDataList);
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
        }
    }

}
