package com.example.pacemaker.util;

public class AuthValidator {
    public static boolean isEmailValid(String email) {
        String emailValidation = "^[_A-Za-z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-z]+$";
        return email.matches(emailValidation);
    }

    public static boolean isPasswordValid(String password) {
        String passwordValidation = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{7,15}.$";
        return password.matches(passwordValidation);
    }
}
