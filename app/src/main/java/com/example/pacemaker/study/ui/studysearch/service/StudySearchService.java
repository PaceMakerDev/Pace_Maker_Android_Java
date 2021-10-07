package com.example.pacemaker.study.ui.studysearch.service;


import com.example.pacemaker.study.ui.studysearch.models.StudyCreateRequestDto;
import com.example.pacemaker.study.ui.studysearch.models.StudyCreateResponseDto;
import com.example.pacemaker.study.ui.studysearch.models.StudyListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface StudySearchService {
    @POST("/v1/studies")
    Call<StudyCreateResponseDto> requestCreateStudy(@Body StudyCreateRequestDto studyCreateRequestDto);

    @GET("/v1/studies/recent")
    Call<StudyListResponse> requestRecentStudyList();

    @GET("/v1/studies/recommend")
    Call<StudyListResponse> requestRecommendStudyList();

}
