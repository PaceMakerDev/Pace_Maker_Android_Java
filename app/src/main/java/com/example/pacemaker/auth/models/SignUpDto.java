package com.example.pacemaker.auth.models;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    @SerializedName("email")
    private String email;

    @SerializedName("major")
    private String major;

    @SerializedName("name")
    private String name;

    @SerializedName("password")
    private String password;
}
