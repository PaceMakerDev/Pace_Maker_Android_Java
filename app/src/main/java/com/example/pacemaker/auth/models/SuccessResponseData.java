package com.example.pacemaker.auth.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponseData {
    private String accessToken;
    private String refreshToken;
    private User user;
    private boolean shouldChangePassword;
}
