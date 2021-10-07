package com.example.pacemaker.study.ui.studysearch.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.fragment.app.Fragment;

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
import com.example.pacemaker.study.ui.mystudy.models.Study;
import com.example.pacemaker.study.ui.studysearch.StudySearchFragment;
import com.example.pacemaker.study.ui.studysearch.models.NewStudy;
import com.example.pacemaker.study.ui.studysearch.models.RecommendStudy;
import com.example.pacemaker.study.ui.studysearch.models.StudyCreateRequestDto;
import com.example.pacemaker.study.ui.studysearch.models.StudyCreateResponseDto;
import com.example.pacemaker.study.ui.studysearch.models.StudyListResponse;
import com.example.pacemaker.study.ui.studysearch.models.StudyOverview;
import com.example.pacemaker.util.DialogUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Request;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudySearchRequest {
    private final StudySearchService service;

    public StudySearchRequest(StudySearchService service) {
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
            }

            @Override
            public void onFailure(Call<StudyCreateResponseDto> call, Throwable t) {
                DialogUtil.showOkDialog(context, "서버 오류", "서버가 응답하지 않습니다");
            }
        });
    }

    public void requestRecentStudy(Context context, StudySearchFragment fragment) {
        service.requestRecentStudyList().enqueue(new Callback<StudyListResponse>() {
            @Override
            public void onResponse(Call<StudyListResponse> call, Response<StudyListResponse> response) {
                switch (response.code()) {
                    case 200:
                        ArrayList<NewStudy> studyList = convertToNewStudy(response.body().getData());
                        fragment.setNewStudy(studyList);
                        break;
                    case 401:
                        Log.d("MyStudy", "Study create err 401");
                        break;

                }
            }

            @Override
            public void onFailure(Call<StudyListResponse> call, Throwable t) {
                DialogUtil.showOkDialog(context, "서버 오류", "서버가 응답하지 않습니다");
            }
        });
    }

    public void requestRecommendStudy(Context context, StudySearchFragment fragment) {
        service.requestRecommendStudyList().enqueue(new Callback<StudyListResponse>() {
            @Override
            public void onResponse(Call<StudyListResponse> call, Response<StudyListResponse> response) {
                switch (response.code()) {
                    case 200:
                        ArrayList<RecommendStudy> studyList = convertToRecommendStudy(response.body().getData());
                        fragment.setRecommendStudy(studyList);
                        break;
                    case 401:
                        Log.d("MyStudy", "Study create err 401");
                        break;
                }
            }

            @Override
            public void onFailure(Call<StudyListResponse> call, Throwable t) {
                DialogUtil.showOkDialog(context, "서버 오류", "서버가 응답하지 않습니다");
            }
        });
    }

    private ArrayList<NewStudy> convertToNewStudy(ArrayList<StudyOverview> data) {
        ArrayList<NewStudy> studyList = new ArrayList<NewStudy>();
        for (StudyOverview overview : data) {
            String tag = "";
            switch(overview.getCategory()) {
                case "LANGUAGE":
                    tag = "어학";
                    break;
                case "IT":
                    tag = "IT/컴퓨터";
                    break;
                case "NATIONAL_EXAM":
                    tag = "고시/공무원";
                    break;
                case "CERTIFICATE":
                    tag = "자격증";
                    break;
                case "EMPLOYMENT":
                    tag = "취업";
                    break;
                case "OTHER":
                    tag = "기타";
                    break;

            }
            String title = overview.getTitle();
            int members = overview.getCapacity();
            int ranking = overview.getRanking();
            NewStudy study = new NewStudy(tag, title, members, ranking);
            studyList.add(study);
        }

        return studyList;
    }

    private ArrayList<RecommendStudy> convertToRecommendStudy(ArrayList<StudyOverview> data) {
        ArrayList<RecommendStudy> studyList = new ArrayList<RecommendStudy>();
        for (StudyOverview overview : data) {
            String tag = "";
            switch(overview.getCategory()) {
                case "LANGUAGE":
                    tag = "어학";
                    break;
                case "IT":
                    tag = "IT/컴퓨터";
                    break;
                case "NATIONAL_EXAM":
                    tag = "고시/공무원";
                    break;
                case "CERTIFICATE":
                    tag = "자격증";
                    break;
                case "EMPLOYMENT":
                    tag = "취업";
                    break;
                case "OTHER":
                    tag = "기타";
                    break;

            }
            String title = overview.getTitle();
            String subTitle = overview.getGoal();
            int members = overview.getCapacity();
            int ranking = overview.getRanking();
            RecommendStudy study = new RecommendStudy(tag, title, subTitle, members, ranking);
            studyList.add(study);
        }

        return studyList;
    }


}
