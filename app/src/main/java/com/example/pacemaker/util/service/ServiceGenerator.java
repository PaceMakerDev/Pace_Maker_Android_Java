package com.example.pacemaker.util.service;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {
    //private static final String BASE_URL = "http://13.124.194.199:8080/";
    //private static final String BASE_URL = "http://13.209.98.129/";
    //private static final String BASE_URL = "http://ec2-13-209-98-129.ap-northeast-2.compute.amazonaws.com";
    public static final String SERVER_URL = "http://13.124.138.39/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        Log.d("MyStudy", "create empty createService");
        return createService(serviceClass, null, null, null);
    }

    public static <S> S createService(Class<S> serviceClass, String authToken, String refreshToken, SharedPreferences.Editor editor) {
        Log.d("MyStudy", "token : " + authToken);
        if (!TextUtils.isEmpty(authToken)) {
            Log.d("MyStudy", "attach interceptor");
            RequestInterceptor interceptor = new RequestInterceptor(SERVER_URL, authToken, refreshToken, editor);
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }
}
