package com.example.pacemaker.studyoverview.service;

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
import com.example.pacemaker.study.ui.studysearch.service.StudySearchService;
import com.example.pacemaker.studyoverview.models.UserRankingDto;
import com.example.pacemaker.studyoverview.ui.overview.OverviewFragment;
import com.example.pacemaker.util.DialogUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudyOverviewRequest {
    private final StudyOverviewService service;

    public StudyOverviewRequest(StudyOverviewService service) {
        this.service = service;
    }

    public void requestUserRanking(String studyId, Context context, OverviewFragment fragment) {
        service.requestUserRankingList(studyId).enqueue(new Callback<UserRankingDto>() {
            @Override
            public void onResponse(Call<UserRankingDto> call, Response<UserRankingDto> response) {
                switch (response.code()) {
                    case 200:
                        fragment.setUserRanking(response.body().getData());
                        break;
                }
            }

            @Override
            public void onFailure(Call<UserRankingDto> call, Throwable t) {
                DialogUtil.showOkDialog(context, "서버 오류", "서버가 응답하지 않습니다");
            }
        });
    }
}
