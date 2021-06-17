package com.example.pacemaker.study.ui.mystudy.models;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GraphResponse {
    private boolean success;
    private ArrayList<BarGraphData> data;
}
