package com.example.pacemaker.study.ui.studysearch.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewStudy {
    private String tag;
    private String title;
    private int members;
    private int ranking;
    private int id;
}
