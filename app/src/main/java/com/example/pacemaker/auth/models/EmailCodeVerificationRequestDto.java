package com.example.pacemaker.auth.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class EmailCodeVerificationRequestDto {
    private String code;
    private String email;
}
