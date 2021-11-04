package com.example.pacemaker.studyoverview.models;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRankingDto {
    private ArrayList<UserRanking> data;
    private boolean success;
}
