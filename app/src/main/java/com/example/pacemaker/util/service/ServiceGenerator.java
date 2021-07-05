package com.example.pacemaker.util.service;

import android.text.TextUtils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {
    //private static final String BASE_URL = "http://13.124.194.199:8080/";
    private static final String BASE_URL = "http://13.209.98.129/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }
    //-----------------------------------------------------sharedpreferences 받아서 업데이트된 accesstoken 적용해줘야함. 또한 현재 refresh token을 사용하여 accestoken받는 과정에서 401에러남. 로그아웃 호출해야함
    public static <S> S createService(Class<S> serviceClass, String authToken, String refreshToken) {
        if (!TextUtils.isEmpty(authToken)) {
            RequestInterceptor interceptor = new RequestInterceptor(BASE_URL, authToken, refreshToken);
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }
}
