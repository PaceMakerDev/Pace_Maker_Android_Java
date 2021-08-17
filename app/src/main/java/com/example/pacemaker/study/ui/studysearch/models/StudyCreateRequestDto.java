package com.example.pacemaker.study.ui.studysearch.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudyCreateRequestDto {
    private String title;
    private int capacity;
    private String category;
    private String goal;
    private String rule;
    private String hashtags;

    @SerializedName("notificationSchedules")
    private ArrayList<StudySchedule> studyScheduleList;
}
