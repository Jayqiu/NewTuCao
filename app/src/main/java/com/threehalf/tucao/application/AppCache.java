package com.threehalf.tucao.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.threehalf.tucao.entity.User;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 保存数据
 *
 * @author jayqiu
 */
public class AppCache {
    private Context mContext;
    private String APPSTOREXML = "com.threehalf.tucao";
    private SharedPreferences mSharedPreferences;
    private final String USER_INFO="user_info";
    private static String SOUNT_NOTICE = "sount_notice";
    private static String VIBRATE_NOTICE = "vibrate_notice";
    private static String ALL_NOTICE = "all_notice";
    private static String COMMENT_NOTICE = "comment_notice";

    public AppCache(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences(APPSTOREXML, Context.MODE_PRIVATE);


    }
    public static AppCache getInstance(Context context) {
        AppCache uniqueInstance = new AppCache(context);
        return uniqueInstance;
    }
    public void saveUserInfo(User userInfo){
       if (userInfo!=null){
        ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
           ObjectOutputStream objectOutputStream=null;
           try {
               objectOutputStream=new ObjectOutputStream(outputStream);
               objectOutputStream.writeObject(userInfo);
           }catch (Exception e){
            e.printStackTrace();
               Log.e("saveUserInfo","saveUserInfo==Exception");
           }
           String userStr=new String(Base64.encodeBase64(outputStream.toByteArray()));
           SharedPreferences.Editor editor=mSharedPreferences.edit();
           editor.putString(USER_INFO,userStr);
           editor.commit();
           editor.clear();

       }

    }
    public User getLoginInfo(){
        User uerInfo=new User();
        String userStr=mSharedPreferences.getString(USER_INFO,"");
        byte [] baseByte=Base64.decodeBase64(userStr.getBytes());
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(baseByte);
        ObjectInputStream objectInputStream=null;
        try{
            objectInputStream= new ObjectInputStream(byteArrayInputStream);
            uerInfo=(User)objectInputStream.readObject();
        }
        catch (Exception e){
            Log.e("getUserInfo","getUserInfo==Exception");
        }

        return uerInfo;
    }
}

