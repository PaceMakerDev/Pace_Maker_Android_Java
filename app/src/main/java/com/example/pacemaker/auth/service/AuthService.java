package com.example.pacemaker.auth.service;

import com.example.pacemaker.auth.models.AuthResponseDto;
import com.example.pacemaker.auth.models.EmailCertificateRequestDto;
import com.example.pacemaker.auth.models.EmailCodeVerificationRequestDto;
import com.example.pacemaker.auth.models.FindEmailRequestDto;
import com.example.pacemaker.auth.models.FindEmailResponseDto;
import com.example.pacemaker.auth.models.FindPwRequestDto;
import com.example.pacemaker.auth.models.FindPwResponseDto;
import com.example.pacemaker.auth.models.SignInDto;
import com.example.pacemaker.auth.models.SignUpDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/v1/auth/signup")
    Call<AuthResponseDto> signUpUser(@Body SignUpDto signUpDto);

    @POST("/v1/auth/signin")
    Call<AuthResponseDto> signInUser(@Body SignInDto signInDto);

    @POST("/v1/auth/email/find")
    Call<FindEmailResponseDto> findEmail(@Body FindEmailRequestDto findEmailRequestDto);

    @POST("/v1/auth/password/find")
    Call<FindPwResponseDto> findPassword(@Body FindPwRequestDto findPwRequestDto);

    @POST("/v1/auth/email/send-code")
    Call<Object> requestCertificateEmail(@Body EmailCertificateRequestDto emailCertificateRequestDto);

    @POST("/v1/auth/email/verify-code")
    Call<Object> requestVerifyEmailCode(@Body EmailCodeVerificationRequestDto emailCodeVerificationRequestDto);
}
