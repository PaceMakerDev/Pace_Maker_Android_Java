package com.example.pacemaker.study.ui.studysearch.service;


import com.example.pacemaker.study.ui.studysearch.models.StudyCreateRequestDto;
import com.example.pacemaker.study.ui.studysearch.models.StudyCreateResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface StudySearchService {
    @POST("/v1/studies")
    Call<StudyCreateResponseDto> requestCreateStudy(@Body StudyCreateRequestDto studyCreateRequestDto);
}
