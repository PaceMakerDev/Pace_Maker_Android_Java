package com.example.pacemaker.study.ui.studysearch.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class NormalStudy {
    private String tag;
    private String title;
    private String subTitle;
    private int members;
    private int ranking;
}
