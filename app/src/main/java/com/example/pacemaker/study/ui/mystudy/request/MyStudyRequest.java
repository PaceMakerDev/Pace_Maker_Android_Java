package com.example.pacemaker.study.ui.mystudy.request;

import android.util.Log;

import com.example.pacemaker.study.ui.mystudy.MyStudyFragment;
import com.example.pacemaker.study.ui.mystudy.enums.GraphType;
import com.example.pacemaker.study.ui.mystudy.models.BarGraphData;
import com.example.pacemaker.study.ui.mystudy.models.GraphResponse;
import com.example.pacemaker.study.ui.mystudy.models.Study;
import com.example.pacemaker.study.ui.mystudy.models.UserStudyResponse;
import com.example.pacemaker.study.ui.mystudy.service.MyStudyService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyStudyRequest {
    private final MyStudyService service;
    private final int userId;

    public MyStudyRequest(MyStudyService service, int userId) {
        this.service = service;
        this.userId = userId;
    }

    public void drawChart(MyStudyFragment fragment, GraphType type) {
        switch (type) {
            case DAILY:
                drawDailyChart(fragment);
                break;
            case WEEKLY:
                drawWeeklyChart(fragment);
                break;
            case MONTHLY:
                drawMonthlyChart(fragment);
                break;
            case ROOM:
                drawStudyChart(fragment);
                break;
        }
    }

    public void drawDailyChart(MyStudyFragment fragment) {
        service.requestDailyStudyData(userId).enqueue(new Callback<GraphResponse>() {
            @Override
            public void onResponse(Call<GraphResponse> call, Response<GraphResponse> response) {
                Log.d("mystudy", response.code() + "");
                switch (response.code()) {
                    case 200:
                        fragment.drawChartByTime(response.body().getData());
                    case 401:

                }
            }

            @Override
            public void onFailure(Call<GraphResponse> call, Throwable t) {
                Log.d("mystudy", "onfailure");
            }
        });
    }

    public void drawWeeklyChart(MyStudyFragment fragment) {
        service.requestWeeklyStudyData(userId).enqueue(new Callback<GraphResponse>() {
            @Override
            public void onResponse(Call<GraphResponse> call, Response<GraphResponse> response) {
                switch (response.code()) {
                    case 200:
                        fragment.drawChartByTime(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<GraphResponse> call, Throwable t) {
                Log.d("mystudy", "onfailure");
            }
        });
    }

    public void drawMonthlyChart(MyStudyFragment fragment) {
        service.requestMonthlyStudyData(userId).enqueue(new Callback<GraphResponse>() {
            @Override
            public void onResponse(Call<GraphResponse> call, Response<GraphResponse> response) {
                switch (response.code()) {
                    case 200:
                        fragment.drawChartByTime(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<GraphResponse> call, Throwable t) {
                Log.d("mystudy", "onfailure");
            }
        });
    }

    public void drawStudyChart(MyStudyFragment fragment) {
        fragment.drawChartByStudy();
        // API : study room별 차트 API 완성 시 구현
        /*
        service.requestMonthlyStudyData(userId).enqueue(new Callback<GraphResponse>() {
            @Override
            public void onResponse(Call<GraphResponse> call, Response<GraphResponse> response) {
                switch (response.code()) {
                    case 200:
                        fragment.drawChartByTime(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<GraphResponse> call, Throwable t) {
                Log.d("mystudy", "onfailure");
            }
        });

         */
    }

    public void showTodayRecord(MyStudyFragment fragment) {
        service.requestDailyStudyData(userId).enqueue(new Callback<GraphResponse>() {
            @Override
            public void onResponse(Call<GraphResponse> call, Response<GraphResponse> response) {
                Log.d("MyStudy", response.code() + "");
                switch (response.code()) {
                    case 200:
                        ArrayList<BarGraphData> dataList = response.body().getData();
                        int record = dataList.get(dataList.size()-1).getYAxis();
                        fragment.showTodayRecord(record);
                }
            }

            @Override
            public void onFailure(Call<GraphResponse> call, Throwable t) {
                Log.d("MyStudy", "onfailure");
            }
        });
    }

    public void showUserStudy(MyStudyFragment fragment) {
        service.requestUserStudyList(userId).enqueue(new Callback<UserStudyResponse>() {
            @Override
            public void onResponse(Call<UserStudyResponse> call, Response<UserStudyResponse> response) {
                Log.d("MyStudy", "showUserStudy() code : " + response.code());
                switch (response.code()) {
                    case 200:
                        ArrayList<Study> studyList = response.body().getStudyList();
                        Log.d("MyStudy", "size : " + studyList.size());
                        if (studyList.size() > 0) {
                            fragment.setStudyList(studyList);
                        }
                        else {
                            fragment.showEmptyStudyList();
                        }
                }
            }

            @Override
            public void onFailure(Call<UserStudyResponse> call, Throwable t) {
                Log.d("MyStudy", "showUserStudy() : Failure\n" + t.getLocalizedMessage());
            }
        });
    }
}
