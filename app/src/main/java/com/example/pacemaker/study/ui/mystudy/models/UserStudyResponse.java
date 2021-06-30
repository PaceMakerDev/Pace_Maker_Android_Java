package com.example.pacemaker.study.ui.mystudy.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserStudyResponse {
    private boolean success;

    @SerializedName("data")
    private ArrayList<Study> studyList;
}
