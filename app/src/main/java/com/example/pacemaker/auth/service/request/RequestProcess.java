package com.example.pacemaker.auth.service.request;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.pacemaker.auth.AuthActivity;
import com.example.pacemaker.auth.enums.FragmentTypes;
import com.example.pacemaker.auth.models.AuthResponseDto;
import com.example.pacemaker.auth.models.EmailCertificateRequestDto;
import com.example.pacemaker.auth.models.EmailCodeVerificationRequestDto;
import com.example.pacemaker.auth.models.FindEmailRequestDto;
import com.example.pacemaker.auth.models.FindEmailResponseDto;
import com.example.pacemaker.auth.models.FindPwRequestDto;
import com.example.pacemaker.auth.models.FindPwResponseDto;
import com.example.pacemaker.auth.models.SignInDto;
import com.example.pacemaker.auth.models.SignUpDto;
import com.example.pacemaker.auth.models.SuccessResponseData;
import com.example.pacemaker.auth.models.User;
import com.example.pacemaker.auth.service.AuthService;
import com.example.pacemaker.auth.ui.signup.EmailCertificationFragment;
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

    public RequestProcess(AuthService service, SharedPreferences preferences) {
        this.service = service;
        this.preferences = preferences;
    }


    public void signIn(SignInDto signInDto, Context loginFragmentContext, AuthActivity activity) {
        service.signInUser(signInDto).enqueue(new Callback<AuthResponseDto>() {
            @Override
            public void onResponse(Call<AuthResponseDto> call, Response<AuthResponseDto> response) {
                switch (response.code()) {
                    case 200:
                        //로그인 성공
                        Log.d(AuthActivity.TAG, "Login Successful");
                        if (response.body().getData().isShouldChangePassword()) {
                            activity.setFragment(FragmentTypes.CHANGE_PASSWORD, null);
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
            }

            @Override
            public void onFailure(Call<AuthResponseDto> call, Throwable t) {
                DialogUtil.showOkDialog(loginFragmentContext, "서버 오류", "서버가 응답하지 않습니다");
            }
        });
    }

    public void signUp(SignUpDto signUpDto, Context signUpFragmentContext, AuthActivity activity) {
        Call<AuthResponseDto> call = service.signUpUser(signUpDto);
        call.enqueue(new Callback<AuthResponseDto>() {
            @Override
            public void onResponse(Call<AuthResponseDto> call, Response<AuthResponseDto> response) {
                switch (response.code()) {
                    case 201:
                        //회원가입 성공
                        updateSharedPreference(response.body());
                        Log.d(AuthActivity.TAG, "Signup Successful");
                        activity.setFragment(FragmentTypes.SIGN_UP_SUCCESS, null);
                        break;
                    case 400:
                        //요청 바디 형식 오류
                        Log.d(AuthActivity.TAG, "SignUp Fail : 요청바디형식오류(400)");
                        break;
                    case 409:
                        //학번 중복
                        Log.d(AuthActivity.TAG, "SignUp Fail : 학번중복(409)");
                        DialogUtil.showOkDialog(signUpFragmentContext, "로그인 오류", "이미 가입된 학번이 있습니다.");
                        break;
                    default:
                        Log.d(AuthActivity.TAG, "SignUp Fail : default" + response.code());
                }
            }

            @Override
            public void onFailure(Call<AuthResponseDto> call, Throwable t) {
                Log.d(AuthActivity.TAG, "SignUp Fail : 서버다운");
                DialogUtil.showOkDialog(signUpFragmentContext, "서버 오류", "서버가 응답하지 않습니다");
            }
        });
    }

    private void updateSharedPreference(AuthResponseDto body) {
        SharedPreferences.Editor editor = preferences.edit();
        SuccessResponseData data = body.getData();
        User user = data.getUser();
        editor.putString(AuthActivity.ACCESS_TOKEN, data.getAccessToken());
        editor.putString(AuthActivity.REFRESH_TOKEN, data.getRefreshToken());
        editor.putString(AuthActivity.USER_NAME, user.getName());
        editor.putInt(AuthActivity.USER_ID, user.getId());
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

    public void findEmail(FindEmailRequestDto findEmailRequestDto, Context findEmailFragmentContext, AuthActivity activity) {
        Call<FindEmailResponseDto> call = service.findEmail(findEmailRequestDto);
        String name = findEmailRequestDto.getName();
        call.enqueue(new Callback<FindEmailResponseDto>() {
            @Override
            public void onResponse(Call<FindEmailResponseDto> call, Response<FindEmailResponseDto> response) {
                switch (response.code()) {
                    case 200:
                        //이메일 찾기 성공
                        Log.d(AuthActivity.TAG, "Find Email Successful");
                        String email = response.body().getUserEmail().getEmail();
                        activity.showSuccessfulEmailFind(name, email);
                        break;
                    case 400:
                        //요청 바디 형식 오류
                        Log.d(AuthActivity.TAG, "Find Email Fail : 요청바디형식오류(400)");
                        break;
                    case 404:
                        //학번 중복
                        Log.d(AuthActivity.TAG, "Find Email Fail : 일치하는 정보 없음(404)");
                        DialogUtil.showOkDialog(findEmailFragmentContext, "아이디 찾기 오류", "일치하는 정보가 없습니다");
                        break;
                    default:
                        Log.d(AuthActivity.TAG, "Find Email Fail : default" + response.code());
                }
            }

            @Override
            public void onFailure(Call<FindEmailResponseDto> call, Throwable t) {
                Log.d(AuthActivity.TAG, "Find Email Fail : 서버다운");
                DialogUtil.showOkDialog(findEmailFragmentContext, "서버 오류", "서버가 응답하지 않습니다");
            }
        });
    }

    public void findPassword(FindPwRequestDto findPwRequestDto, Context findPwFragmentContext, AuthActivity activity) {
        Call<FindPwResponseDto> call = service.findPassword(findPwRequestDto);
        Log.d("Auth", requestBody2String(call.request()));
        call.enqueue(new Callback<FindPwResponseDto>() {
            @Override
            public void onResponse(Call<FindPwResponseDto> call, Response<FindPwResponseDto> response) {
                switch (response.code()) {
                    case 200:
                        //비밀번호 찾기 성공
                        Log.d(AuthActivity.TAG, "Find Password Successful");
                        String email = response.body().getUserNameAndEmail().getEmail();
                        String name = response.body().getUserNameAndEmail().getName();
                        activity.showSuccessfulPasswordFind(name, email);
                        break;
                    case 400:
                        //요청 바디 형식 오류
                        Log.d(AuthActivity.TAG, "Find Password Fail : 요청바디형식오류(400)");
                        break;
                    case 404:
                        //학번 중복
                        Log.d(AuthActivity.TAG, "Find Password Fail : 일치하는 정보 없음(404)");
                        DialogUtil.showOkDialog(findPwFragmentContext, "비밀번호 찾기 오류", "일치하는 정보가 없습니다");
                        break;
                    case 500:
                        Log.d(AuthActivity.TAG, "Find Password Fail : 서버오류로 이메일을 못보냄");
                        DialogUtil.showOkDialog(findPwFragmentContext, "비밀번호 찾기 오류", "죄송합니다. 서버 오류로 인해 해당 이메일로 임시 비밀번호를 보내지 못했습니다. 다시 시도해주세요");
                        break;
                    default:
                        Log.d(AuthActivity.TAG, "Find Email Fail : default" + response.code());
                }
            }

            @Override
            public void onFailure(Call<FindPwResponseDto> call, Throwable t) {
                Log.d(AuthActivity.TAG, "Find Password Fail : 서버다운");
                DialogUtil.showOkDialog(findPwFragmentContext, "서버 오류", "서버가 응답하지 않습니다");
            }
        });
    }

    public void requestEmailCertification(EmailCertificateRequestDto emailCertificateRequestDto, Context context, AuthActivity activity) {
        Call<Object> call = service.requestCertificateEmail(emailCertificateRequestDto);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("Auth", response.code() + "");
                if (response.code() == 204) {
                    String email = emailCertificateRequestDto.getEmail();
                    Bundle bundle= new Bundle();
                    bundle.putString("email", email);
                    activity.setFragment(FragmentTypes.AUTHENTICATE_EMAIL, bundle);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d(AuthActivity.TAG, "SignUp Fail : 서버다운");
                DialogUtil.showOkDialog(context, "서버 오류", "서버가 응답하지 않습니다");
                Log.d("Auth", t.getLocalizedMessage());
            }
        });
    }

    public void requestEmailVerification(EmailCodeVerificationRequestDto emailCodeVerificationRequestDto, EmailCertificationFragment fragment, AuthActivity activity) {
        Call<Object> call = service.requestVerifyEmailCode(emailCodeVerificationRequestDto);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("Auth", response.code() + "");
                switch (response.code()) {
                    case 204:
                        activity.setFragment(FragmentTypes.SIGNUP, null);
                        break;
                    case 404:
                        fragment.showNoValidCodeError();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d(AuthActivity.TAG, "SignUp Fail : 서버다운");
                DialogUtil.showOkDialog(fragment.requireContext(), "서버 오류", "서버가 응답하지 않습니다");
                Log.d("Auth", t.getLocalizedMessage());
            }
        });
    }
}
