package com.example.pacemaker.studyoverview.service;


import com.example.pacemaker.study.ui.studysearch.models.StudyCreateRequestDto;
import com.example.pacemaker.study.ui.studysearch.models.StudyCreateResponseDto;
import com.example.pacemaker.study.ui.studysearch.models.StudyListResponse;
import com.example.pacemaker.studyoverview.models.UserRankingDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StudyOverviewService {
    /*
    @POST("/v1/studies")
    Call<StudyCreateResponseDto> requestCreateStudy(@Body StudyCreateRequestDto studyCreateRequestDto);
    @GET("/v1/studies")
    Call<StudyListResponse> requestStudySearchList(@Query("search") String input);
     */
    @GET("/studies/{studyId}/user-rankings")
    Call<UserRankingDto> requestUserRankingList(@Path("studyId") String input);

}
