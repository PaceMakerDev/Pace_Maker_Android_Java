package com.example.pacemaker.study.commonservice;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private boolean success;

    @SerializedName("data")
    private TokenData tokenData;
}
