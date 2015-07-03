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



    private static String b = "sount_notice";
    private static String c = "vibrate_notice";
    private static String d = "all_notice";
    private static String e = "comment_notice";



    /**
     * 设置总开关
     * @param paramBoolean
     */
    public void setAllNotice(boolean paramBoolean)
    {
        SharedPreferences.Editor localEditor = mSharedPreferences.edit();
        localEditor.putBoolean(ALL_NOTICE, paramBoolean);
        localEditor.commit();
        localEditor.clear();
    }
    /**
     * 获取总提示
     * @return
     */
    public boolean getAllNotice()
    {
        return mSharedPreferences.getBoolean(ALL_NOTICE, true);
    }
    /**
     * 获取声音提示
     * @return
     */
    public boolean getSuntNotice()
    {
        return this.mSharedPreferences.getBoolean(SOUNT_NOTICE, true);
    }

    /**
     * 设置声音提示
     * @param paramBoolean
     */
    public void setSountNotice(boolean paramBoolean)
    {
        SharedPreferences.Editor localEditor = mSharedPreferences.edit();
        localEditor.putBoolean(SOUNT_NOTICE, paramBoolean);
        localEditor.commit();
        localEditor.clear();
    }
    /**
     * 设置震动提示
     * @param paramBoolean
     */
    public void setVibrateNotice(boolean paramBoolean)
    {
        SharedPreferences.Editor localEditor = mSharedPreferences.edit();
        localEditor.putBoolean(VIBRATE_NOTICE, paramBoolean);
        localEditor.commit();
        localEditor.clear();
    }
    /**
     * 获取震动提示
     * @return
     */
    public boolean getVibrateNotice()
    {
        return this.mSharedPreferences.getBoolean(VIBRATE_NOTICE, true);
    }

    /**
     * 设置评论提示
     * @param paramBoolean
     */
    public  void setCommentNotice(boolean paramBoolean)
    {
        SharedPreferences.Editor localEditor = mSharedPreferences.edit();
        localEditor.putBoolean(COMMENT_NOTICE, paramBoolean);
        localEditor.commit();
        localEditor.clear();
    }

    /**
     * 获取震动提示
     * @return
     */
    public boolean getCommentNotice()
    {
        return this.mSharedPreferences.getBoolean(COMMENT_NOTICE, true);
    }

}

