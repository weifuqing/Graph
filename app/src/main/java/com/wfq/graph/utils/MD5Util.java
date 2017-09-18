package com.wfq.graph.utils;

import com.qiniu.android.dns.util.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 */
public class MD5Util {

    public static String encode(String str){
        String encodeStr = "";
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(str.getBytes());
            byte[] shaBytes = digest.digest();
            encodeStr = Hex.encodeHexString(shaBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
}
