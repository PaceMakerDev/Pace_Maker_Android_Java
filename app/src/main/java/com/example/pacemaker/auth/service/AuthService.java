package com.example.pacemaker.auth.service;

import com.example.pacemaker.auth.models.AuthResponseDto;
import com.example.pacemaker.auth.models.SignUpDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/v1/auth/sign-up")
    Call<AuthResponseDto> signUpUser(@Body SignUpDto signUpDto);

}
