package com.example.pacemaker.study.ui.studysearch.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class NormalStudy extends NewStudy{
    private String subTitle;
    public NormalStudy(String tag, String title, String subTitle, int members, int ranking, int id) {
        super(tag, title, members, ranking, id);
        this.subTitle = subTitle;
    }
}
