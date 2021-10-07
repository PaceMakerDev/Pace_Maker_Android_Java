package com.example.pacemaker.study.ui.studysearch.models;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class StudyListResponse {
    private boolean success;
    private ArrayList<StudyOverview> data;
}
