package com.evaluation.service.services;

import com.softobt.core.exceptions.models.RestServiceException;
import com.softobt.core.logger.services.LoggerService;

import java.security.MessageDigest;

public class HashingService {
    public static final String SHA_256 = "SHA-256";

    public static String applyHash(String input, String hashType)throws Exception{
        MessageDigest digest = MessageDigest.getInstance(hashType);
        byte[] hash = digest.digest(input.getBytes("UTF-8"));
        return hasher(hash);
    }

    private static String hasher(byte[] hash){
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
