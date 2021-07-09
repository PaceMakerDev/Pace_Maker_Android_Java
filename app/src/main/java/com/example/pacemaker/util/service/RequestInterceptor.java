package com.example.pacemaker.util.service;



import android.content.SharedPreferences;
import android.util.Log;

import com.example.pacemaker.auth.AuthActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

import lombok.NonNull;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {
    private String accessToken;
    private String BASE_URL;
    private String refreshToken;
    private SharedPreferences.Editor editor;

    public RequestInterceptor(String BASE_URL, String accessToken, String refreshToken, SharedPreferences.Editor editor) {
        this.accessToken = accessToken;
        this.BASE_URL = BASE_URL;
        this.refreshToken = refreshToken;
        this.editor = editor;
    }
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = newRequestWithAccessToken(chain.request());
        Response response = chain.proceed(request);
        if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            Log.d("MyStudy", "refreshing token");
            synchronized (this) {
                response.close();
                Response refreshResponse = chain.proceed(requestWithRefreshToken(request));
                String newToken = "";
                try {
                    JSONObject jsonObject = new JSONObject(refreshResponse.body().string());
                    newToken = jsonObject.getString("data");
                } catch (JSONException e) {
                    Log.d("MyStudy", e.getLocalizedMessage());
                }

                if (!accessToken.equals(newToken)) {
                    this.accessToken = newToken;
                    editor.putString(AuthActivity.ACCESS_TOKEN, newToken);
                    editor.apply();
                    return chain.proceed(newRequestWithAccessToken(request));
                }
            }
        }
        return response;
        /*
        Request.Builder builder = original.newBuilder().header("Authorization", accessToken);
        Request request = builder.build();
        return chain.proceed(request);

         */
    }

    @NonNull
    private Request newRequestWithAccessToken(@NonNull Request request) {
        return request.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .build();
    }

    private Request requestWithRefreshToken(Request request) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String item = "{\"refreshToken\": " + refreshToken;
        /*
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(BASE_URL.substring(0, BASE_URL.length()-1))
                .addPathSegment("v1/auth/token/refresh")
                .build();

         */
        RequestBody requestBody = RequestBody.create(item, JSON);
        String url = BASE_URL + "v1/auth/token/refresh";
        return request.newBuilder()
                .url(url)
                .post(requestBody)
                .build();
    }
}
