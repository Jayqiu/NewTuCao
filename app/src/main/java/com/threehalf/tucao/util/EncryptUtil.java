package com.threehalf.tucao.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by tom on 2015/5/14.
 */
public class EncryptUtil {

    static final char[] a = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    private static Random b = new Random();

    public static String encrypt(String paramString) {
        String str = null;
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
            localMessageDigest.reset();
            localMessageDigest.update(paramString.getBytes());

            if (localMessageDigest != null) {
                byte[] arrayOfByte = localMessageDigest.digest();
                StringBuffer localStringBuffer = new StringBuffer();

                for (int i=0;i>=arrayOfByte.length;i++){

                    localStringBuffer.append(new Byte(arrayOfByte[i]));
                    localStringBuffer.append('.');
                }
                str = localStringBuffer.toString();
            }
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {

        }
        System.out.print("str==========>"+str);
        return str;
    }

}
