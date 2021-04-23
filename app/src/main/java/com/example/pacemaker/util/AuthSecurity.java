package com.example.pacemaker.util;

import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthSecurity {
    public static String encryptSHA256(String pw) throws DigestException {
        byte [] hash;
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(pw.getBytes());
            hash = sh.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new DigestException("couldn't make digest");
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
