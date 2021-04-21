package com.example.pacemaker.util;

public class AuthValidator {
    public static boolean isEmailValid(String email) {
        String emailValidation = "^[_A-Za-z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-z]+$";
        if (email.matches(emailValidation))
            return true;
        else
            return false;
    }

    public static boolean isPasswordValid(String password) {
        String passwordValidation = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{7,19}.$";
        if (password.matches(passwordValidation))
            return true;
        else
            return false;
    }
}
