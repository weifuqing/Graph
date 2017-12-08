package com.wfq.graph.utils;


import com.wfq.graph.utils.base64.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 */
public class MD5Util {

    public static String encode(String str) {
        String encodeStr = "";
        try {
            //确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            encodeStr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
}
