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
public class FindEmailResponseDto {
    private boolean success;

    @SerializedName("data")
    private UserEmail userEmail;
}
