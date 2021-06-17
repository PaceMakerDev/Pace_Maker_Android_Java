package com.example.pacemaker.study.ui.mystudy.request;

import android.util.Log;

import com.example.pacemaker.study.ui.mystudy.MyStudyFragment;
import com.example.pacemaker.study.ui.mystudy.enums.GraphType;
import com.example.pacemaker.study.ui.mystudy.models.GraphResponse;
import com.example.pacemaker.study.ui.mystudy.service.MyStudyService;

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
        Log.d("mystudy", "drawChart");
        switch (type) {
            case DAILY:
                drawDailyChart(fragment);
                Log.d("mystudy", "daily");
                break;
            case WEEKLY:
                drawWeeklyChart(fragment);
                break;
            case MONTHLY:
                drawMonthlyChart(fragment);
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
                        fragment.drawChart(response.body().getData());
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
                        fragment.drawChart(response.body().getData());
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
                        fragment.drawChart(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<GraphResponse> call, Throwable t) {
                Log.d("mystudy", "onfailure");
            }
        });
    }
}
