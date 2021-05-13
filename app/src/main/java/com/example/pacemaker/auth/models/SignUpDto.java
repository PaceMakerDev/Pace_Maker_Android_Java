package com.example.pacemaker.auth.models;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private String email;
    private String name;
    private String major;
    private String studentId;
    private String password;
    private String birthday;
    private String academicStatus;
}
