package com.example.pacemaker.auth.models;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;
}
