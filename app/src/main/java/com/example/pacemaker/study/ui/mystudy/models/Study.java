package com.example.pacemaker.study.ui.mystudy.models;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Study {
    private int id;
    private String title;
    private int studyRanking;
    private int userRankingInStudy;
    private int studyTimeInSecond;
    private ArrayList<String> tag;
}
