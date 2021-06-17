package com.example.pacemaker.study.ui.mystudy.service;

import com.example.pacemaker.auth.models.AuthResponseDto;
import com.example.pacemaker.auth.models.FindEmailRequestDto;
import com.example.pacemaker.auth.models.FindEmailResponseDto;
import com.example.pacemaker.auth.models.FindPwRequestDto;
import com.example.pacemaker.auth.models.FindPwResponseDto;
import com.example.pacemaker.auth.models.SignInDto;
import com.example.pacemaker.auth.models.SignUpDto;
import com.example.pacemaker.study.ui.mystudy.models.BarGraphData;
import com.example.pacemaker.study.ui.mystudy.models.GraphResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyStudyService {
    @GET("/v1/users/{userId}/study-logs/daily")
    Call<GraphResponse> requestDailyStudyData(@Path("userId") int userId);

    @GET("/v1/users/{userId}/study-logs/monthly")
    Call<GraphResponse> requestMonthlyStudyData(@Path("userId") int userId);

    @GET("/v1/users/{userId}/study-logs/weekly")
    Call<GraphResponse> requestWeeklyStudyData(@Path("userId") int userId);
}
