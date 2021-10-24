package com.example.springcinemawebapp.utils;

import org.springframework.util.Base64Utils;

public class EncryptionUtils {
    public static long decryptId(String encryptedId, int rounds) {
        for (int i = 0; i < rounds; i++) {
            encryptedId = new String(Base64Utils.decodeFromString(encryptedId));
        }
        return Long.parseLong(encryptedId);
    }
}
