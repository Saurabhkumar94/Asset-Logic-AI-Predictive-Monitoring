package com.asset.util;

import java.security.MessageDigest;
import java.util.Base64;

public class SecurityUtil {
    // SHA-256 logic: Audit logs ko tamper-proof banane ke liye [cite: 29, 156]
    public static String calculateHash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Hash calculation failed!");
        }
    }
}