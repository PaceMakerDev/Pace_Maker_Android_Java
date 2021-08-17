package com.example.pacemaker.study.ui.studysearch.models;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudyOverview {
    private int id;
    private String title;
    private String goal;
    private int capacity;
    private int ranking;
    private String category;
    private ArrayList<String> hashtags;
    private ArrayList<StudySchedule> notificationSchedules;
}
