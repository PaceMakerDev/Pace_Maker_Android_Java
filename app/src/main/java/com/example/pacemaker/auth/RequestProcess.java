package com.example.pacemaker.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.pacemaker.auth.models.AuthResponseDto;
import com.example.pacemaker.auth.models.SignInDto;
import com.example.pacemaker.auth.models.SignUpDto;
import com.example.pacemaker.auth.models.SuccessResponseData;
import com.example.pacemaker.auth.models.User;
import com.example.pacemaker.auth.service.AuthService;
import com.example.pacemaker.util.DialogUtil;

import java.io.IOException;

import okhttp3.Request;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
    signIn
        로그인시 home 메뉴로 이동
    signUp
        회원가입시 입력된 정보를 바탕으로 home메뉴로 이동
 */

public class RequestProcess {
    private final AuthService service;
    private final SharedPreferences preferences;

    public RequestProcess(AuthService service, SharedPreferences preferences ) {
        this.service = service;
        this.preferences = preferences;
    }

    public void signIn(SignInDto signInDto, Context loginFragmentContext) {
        service.signInUser(signInDto).enqueue(new Callback<AuthResponseDto>() {
            @Override
            public void onResponse(Call<AuthResponseDto> call, Response<AuthResponseDto> response) {
                switch (response.code()) {
                    case 200:
                        //로그인 성공
                        updateSharedPreference(response.body());
                        Log.d(MainActivity.TAG, "Login Successful");
                        Intent intent = new Intent(loginFragmentContext, com.example.pacemaker.study.MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        loginFragmentContext.startActivity(intent);
                        break;
                    case 400:
                        //요청바디형식 잘못됨
                        Log.d(MainActivity.TAG, "Login fail : 요청바디형식 잘못됨(400)");
                        break;
                    case 404:
                        Log.d(MainActivity.TAG, "Login fail : 아이디 비번 일치 안함(404)");
                        DialogUtil.showOkDialog(loginFragmentContext, "로그인 오류", "아이디 혹은 비밀번호가 일치하지 않습니다");
                        break;
                    default:
                        Log.d(MainActivity.TAG, "Login fail : default");
                }
            }

            @Override
            public void onFailure(Call<AuthResponseDto> call, Throwable t) {
                DialogUtil.showOkDialog(loginFragmentContext, "서버 오류", "서버가 응답하지 않습니다");
            }
        });
    }

    public void signUp(SignUpDto signUpDto, Context signUpFragmentContext) {
        Call<AuthResponseDto> call = service.signUpUser(signUpDto);
        call.enqueue(new Callback<AuthResponseDto>() {
            @Override
            public void onResponse(Call<AuthResponseDto> call, Response<AuthResponseDto> response) {
                switch (response.code()) {
                    case 201:
                        //회원가입 성공
                        updateSharedPreference(response.body());
                        Log.d(MainActivity.TAG, "Signup Successful");
                        Intent intent = new Intent(signUpFragmentContext, com.example.pacemaker.study.MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        signUpFragmentContext.startActivity(intent);
                        break;
                    case 400:
                        //요청 바디 형식 오류
                        Log.d(MainActivity.TAG, "SignUp Fail : 요청바디형식오류(400)");
                        break;
                    case 409:
                        //학번 중복

                        Log.d(MainActivity.TAG, "SignUp Fail : 학번중복(409)");
                        DialogUtil.showOkDialog(signUpFragmentContext, "로그인 오류", "이미 가입된 학번이 있습니다.");
                        break;
                    default:
                        Log.d(MainActivity.TAG, "SignUp Fail : default" + response.code());
                }
            }

            @Override
            public void onFailure(Call<AuthResponseDto> call, Throwable t) {
                Log.d(MainActivity.TAG, "SignUp Fail : 서버다운");
                DialogUtil.showOkDialog(signUpFragmentContext, "서버 오류", "서버가 응답하지 않습니다");
            }
        });
    }

    private void updateSharedPreference(AuthResponseDto body) {
        SharedPreferences.Editor editor = preferences.edit();
        SuccessResponseData data = body.getData();
        User user = data.getUser();
        editor.putString("accessToken", data.getAccessToken());
        editor.putString("refreshToken", data.getRefreshToken());
        editor.putString("userName", user.getName());
        editor.putInt("userId", user.getId());
        editor.apply();
    }

    private String requestBody2String(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
