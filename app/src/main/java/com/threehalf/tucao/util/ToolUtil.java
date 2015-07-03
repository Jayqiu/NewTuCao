package com.threehalf.tucao.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *@author jayqiu
 */
public class ToolUtil {
    public static boolean isEmail(String email){
        String str="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
