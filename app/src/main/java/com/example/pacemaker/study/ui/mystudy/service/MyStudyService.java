package com.example.pacemaker.study.ui.mystudy.service;

import com.example.pacemaker.auth.models.AuthResponseDto;
import com.example.pacemaker.auth.models.FindEmailRequestDto;
import com.example.pacemaker.auth.models.FindEmailResponseDto;
import com.example.pacemaker.auth.models.FindPwRequestDto;
import com.example.pacemaker.auth.models.FindPwResponseDto;
import com.example.pacemaker.auth.models.SignInDto;
import com.example.pacemaker.auth.models.SignUpDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MyStudyService {
    @POST("/v1/auth/email/find")
    Call<FindEmailResponseDto> findEmail(@Body FindEmailRequestDto findEmailRequestDto);
}
