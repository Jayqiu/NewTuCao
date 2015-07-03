package com.threehalf.tucao.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.bmob.utils.BmobLog;
import com.threehalf.tucao.entity.AlertEntity;
import com.threehalf.tucao.entity.MessageModel;
import com.threehalf.tucao.util.CommonUtils;

import org.json.JSONObject;

import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;

/**
 * Created by Administrator on 2015/7/1.
 */
public class MyPushMessageReceiver extends BroadcastReceiver {
    BmobUserManager userManager;
    BmobChatUser currentUser;
    @Override
    public void onReceive(Context context, Intent intent) {
    String json = intent.getStringExtra("msg");
        userManager = BmobUserManager.getInstance(context);
        currentUser = userManager.getCurrentUser();
        boolean  isNetConnected= CommonUtils.isNetworkAvailable(context);
        if (isNetConnected){

        }
    }


    private void parseMessage(Context paramContext, String paramString)
    {
        try
        {
//            initNotification(paramContext);
            MessageModel localMessageModel = (MessageModel) JSON.parseObject(paramString, MessageModel.class);
            String str1 = localMessageModel.getAlert();
            if ((str1 != null) && (str1.length() > 0))
            {
                JSONObject localJSONObject = new JSONObject(str1);
                String str2 = localJSONObject.getString("pType").toString();
                String str3 = localJSONObject.getString("tObj").toString();
                String str4 = localJSONObject.getString("msg").toString();
                if (str2.equals("1000"))
                {
                    AlertEntity localAlertEntity = new AlertEntity();
                    localAlertEntity.setpType(str2);
                    localAlertEntity.settObj(str3);
                    localAlertEntity.setMsg(str4);
//                    updatePushContent(paramContext, localMessageModel, localAlertEntity);
                }
            }
            if ((localMessageModel.getTid() != null) && (localMessageModel.getTid().length() > 0) && (localMessageModel.getTid().equals(this.currentUser.getObjectId())))
            {
                if (localMessageModel.getfId().equals(this.currentUser.getObjectId()))
                {
                    BmobLog.i("parseMessage错误：自己评论自己的");
                    return;
                }
               // updateContent(paramContext, localMessageModel);
                return;
            }
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
            BmobLog.i("parseMessage错误：" + localException.getMessage());
        }
    }
}
