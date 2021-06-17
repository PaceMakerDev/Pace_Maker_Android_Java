package com.example.pacemaker.study.commonservice;

import android.media.session.MediaSession;

import com.example.pacemaker.auth.models.FindEmailRequestDto;
import com.example.pacemaker.auth.models.FindEmailResponseDto;
import com.example.pacemaker.study.ui.mystudy.models.GraphResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommonService {
    @POST("/v1/auth/token/refresh")
    Call<TokenResponse> requestRefreshToken(@Body TokenRequestDto refreshToken);
}
