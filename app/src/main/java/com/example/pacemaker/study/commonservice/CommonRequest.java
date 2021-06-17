package com.example.pacemaker.study.commonservice;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pacemaker.auth.AuthActivity;
import com.example.pacemaker.study.StudyActivity;
import com.example.pacemaker.study.ui.mystudy.enums.GraphType;
import com.example.pacemaker.util.DialogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonRequest {
    private final CommonService service;
    private final SharedPreferences preferences;

    public CommonRequest(CommonService service, SharedPreferences preferences) {
        this.service = service;
        this.preferences = preferences;
    }

    public void refreshToken(AppCompatActivity activity) {
        String oldToken = preferences.getString(AuthActivity.REFRESH_TOKEN, null);
        // token request를 execute()로 하는 것 생각해보기
        service.requestRefreshToken(new TokenRequestDto(oldToken)).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                switch (response.code()) {
                    case 200:
                        String newToken = response.body().getTokenData().getAccessToken();
                        Log.d("StudyCommon", "success");
                        preferences.edit().putString("accessToken", newToken).apply();
                        break;
                    case 400:
                        //사용자 예외 처리 필요
                        Log.d("StudyCommon", "requset body 잘못됨");
                        Log.d("StudyCommon", response.message());
                        break;
                    default:
                        DialogUtil.showOkDialog(activity.getBaseContext(), "토큰 오류",
                                "토큰이 만료되었습니다. 다시 로그인해주세요");
                        // 로그아웃 기능을 dialog ok 버튼에 달아야 함
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.d("StudyCommon", "onFailure refresh token");
                Log.d("StudyCommon", t.getLocalizedMessage());
            }
        });
    }
}
