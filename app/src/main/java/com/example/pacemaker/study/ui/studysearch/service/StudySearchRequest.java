package com.example.pacemaker.study.ui.studysearch.service;

import android.content.Context;
import android.util.Log;

import com.example.pacemaker.study.StudyActivity;
import com.example.pacemaker.study.enums.FragmentTypes;
import com.example.pacemaker.study.ui.studysearch.StudySearchFragment;
import com.example.pacemaker.study.ui.studysearch.models.NewStudy;
import com.example.pacemaker.study.ui.studysearch.models.NormalStudy;
import com.example.pacemaker.study.ui.studysearch.models.StudyCreateRequestDto;
import com.example.pacemaker.study.ui.studysearch.models.StudyCreateResponseDto;
import com.example.pacemaker.study.ui.studysearch.models.StudyListResponse;
import com.example.pacemaker.study.ui.studysearch.models.StudyOverview;
import com.example.pacemaker.util.DialogUtil;

import java.util.ArrayList;

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
                        ArrayList<NormalStudy> studyList = convertToRecommendStudy(response.body().getData());
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

    public void requestStudySearch(Context context, StudySearchFragment fragment, String input) {
        service.requestStudySearchList(input).enqueue(new Callback<StudyListResponse>() {
            @Override
            public void onResponse(Call<StudyListResponse> call, Response<StudyListResponse> response) {
                switch (response.code()) {
                    case 200:
                        ArrayList<NormalStudy> studyList = convertToRecommendStudy(response.body().getData());
                        fragment.setStudySearch(studyList);
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
            int id = overview.getId();
            NewStudy study = new NewStudy(tag, title, members, ranking, id);
            studyList.add(study);
        }

        return studyList;
    }

    private ArrayList<NormalStudy> convertToRecommendStudy(ArrayList<StudyOverview> data) {
        ArrayList<NormalStudy> studyList = new ArrayList<NormalStudy>();
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
            int id = overview.getId();
            NormalStudy study = new NormalStudy(tag, title, subTitle, members, ranking, id);
            studyList.add(study);
        }

        return studyList;
    }


}
