package com.example.pacemaker.study.ui.studysearch.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.pacemaker.auth.AuthActivity;
import com.example.pacemaker.auth.models.AuthResponseDto;
import com.example.pacemaker.auth.models.FindEmailRequestDto;
import com.example.pacemaker.auth.models.FindEmailResponseDto;
import com.example.pacemaker.auth.models.FindPwRequestDto;
import com.example.pacemaker.auth.models.FindPwResponseDto;
import com.example.pacemaker.auth.models.SignInDto;
import com.example.pacemaker.auth.models.SignUpDto;
import com.example.pacemaker.auth.models.SuccessResponseData;
import com.example.pacemaker.auth.models.User;
import com.example.pacemaker.auth.service.AuthService;
import com.example.pacemaker.study.StudyActivity;
import com.example.pacemaker.study.enums.FragmentTypes;
import com.example.pacemaker.study.ui.studysearch.models.StudyCreateRequestDto;
import com.example.pacemaker.study.ui.studysearch.models.StudyCreateResponseDto;
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

public class StudyCreateRequest {
    private final StudySearchService service;

    public StudyCreateRequest(StudySearchService service) {
        this.service = service;
    }


    public void createStudy(StudyCreateRequestDto studyCreateRequestDto, Context context, StudyActivity activity) {
        service.requestCreateStudy(studyCreateRequestDto).enqueue(new Callback<StudyCreateResponseDto>() {
            @Override
            public void onResponse(Call<StudyCreateResponseDto> call, Response<StudyCreateResponseDto> response) {
                switch (response.code()) {
                    case 201:
                        activity.setFragment(FragmentTypes.STUDY_CREATE_SUCCESS);
                        break;
                    case 401:
                        Log.d("MyStudy", "Study create err 401");
                        break;
                }
                /*
                switch (response.code()) {

                    case 201:
                        Log.d(AuthActivity.TAG, "Login Successful");
                        if (response.body().getData().isShouldChangePassword()) {
                            activity.setFragment(FragmentTypes.CHANGE_PASSWORD);
                        }
                        else {
                            updateSharedPreference(response.body());
                            Intent intent = new Intent(loginFragmentContext, com.example.pacemaker.study.StudyActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            loginFragmentContext.startActivity(intent);
                        }
                        break;
                    case 400:
                        //요청바디형식 잘못됨
                        Log.d(AuthActivity.TAG, "Login fail : 요청바디형식 잘못됨(400)");
                        break;
                    case 404:
                        Log.d(AuthActivity.TAG, "Login fail : 아이디 비번 일치 안함(404)");
                        DialogUtil.showOkDialog(loginFragmentContext, "로그인 오류", "아이디 혹은 비밀번호가 일치하지 않습니다");
                        break;
                    default:
                        Log.d(AuthActivity.TAG, "Login fail : default");
                }

                 */
            }

            @Override
            public void onFailure(Call<StudyCreateResponseDto> call, Throwable t) {
                DialogUtil.showOkDialog(context, "서버 오류", "서버가 응답하지 않습니다");
            }
        });
    }


}
