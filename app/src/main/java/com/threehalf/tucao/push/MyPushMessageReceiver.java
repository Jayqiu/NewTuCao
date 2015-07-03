package com.threehalf.tucao.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.bmob.utils.BmobLog;
import com.threehalf.tucao.R;
import com.threehalf.tucao.activity.ActMain;
import com.threehalf.tucao.activity.ActTucaoDetail;
import com.threehalf.tucao.application.AppCache;
import com.threehalf.tucao.entity.AlertEntity;
import com.threehalf.tucao.entity.MessageModel;
import com.threehalf.tucao.entity.TuCao;
import com.threehalf.tucao.util.CommonUtils;
import com.threehalf.tucao.util.ConstantUtil;

import org.json.JSONObject;

import cn.bmob.im.BmobNotifyManager;
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
        boolean isNetConnected = CommonUtils.isNetworkAvailable(context);
        if (isNetConnected) {
            parseMessage(context, json);
        }
    }


    private void parseMessage(Context paramContext, String paramString) {
        try {
            MessageModel localMessageModel = (MessageModel) JSON.parseObject(paramString, MessageModel.class);
            String str1 = localMessageModel.getAlert();
            if ((str1 != null) && (str1.length() > 0)) {
                JSONObject localJSONObject = new JSONObject(str1);
                String str2 = localJSONObject.getString("pType").toString();
                String str3 = localJSONObject.getString("tObj").toString();
                String str4 = localJSONObject.getString("msg").toString();
                if (str2.equals("1000")) {
                    AlertEntity localAlertEntity = new AlertEntity();
                    localAlertEntity.setpType(str2);
                    localAlertEntity.settObj(str3);
                    localAlertEntity.setMsg(str4);
                    updatePushContent(paramContext, localMessageModel, localAlertEntity);
                }
            }
            if ((localMessageModel.getTid() != null) && (localMessageModel.getTid().length() > 0) && (localMessageModel.getTid().equals(this.currentUser.getObjectId()))) {
                if (localMessageModel.getfId().equals(this.currentUser.getObjectId())) {
                    BmobLog.i("parseMessage错误：自己评论自己的");
                    return;
                }
//                 updateContent(paramContext, localMessageModel);
                return;
            }
        } catch (Exception localException) {
            localException.printStackTrace();
            BmobLog.i("parseMessage错误：" + localException.getMessage());
        }
    }

    private void updatePushContent(Context paramContext, MessageModel localMessageModel, AlertEntity localAlertEntity) {
        String str1 = "";
        String str2 = "";
        if ((localAlertEntity.gettObj() != null) && (localAlertEntity.gettObj().length() > 0)) {
            if ("1000".equals(localAlertEntity.getpType())){// 系统推荐吐槽
                Bundle localBundle = new Bundle();
                Intent localIntent = new Intent();
                localIntent.setClass(paramContext.getApplicationContext(), ActTucaoDetail.class);
                TuCao localTuCao = new TuCao();
                localTuCao.setObjectId(localAlertEntity.gettObj());
                str1 = "为你推荐";
                str2 = localAlertEntity.getMsg();
                localBundle.putSerializable("tucaoEntity", localTuCao);
                localBundle.putString("intentFlag", ConstantUtil.INTENT_TUCAO_COMENT_MSG);
                localIntent.putExtras(localBundle);
                showNotitfy(paramContext, localIntent, str2, str1, localAlertEntity.getpType());
            }
        }
    }

    public void showNotitfy(Context paramContext, Intent paramIntent, String tickerText, String contentTitle, String msgType) {
        AppCache appCache = AppCache.getInstance(paramContext);
        boolean isAllowVoice = appCache.getSuntNotice();
        boolean isAllowVibrate = appCache.getVibrateNotice();
        boolean all_notice = appCache.getAllNotice();
        boolean comment_notice = appCache.getCommentNotice();
        if (all_notice) {
            if (msgType.equals("1000")) {// 系统推荐
                BmobNotifyManager.getInstance(paramContext).showNotifyWithExtras(isAllowVoice, isAllowVibrate, R.mipmap.ic_launcher, tickerText, contentTitle, tickerText.toString(), paramIntent);
            } else {
                if (comment_notice){
                    BmobNotifyManager.getInstance(paramContext).showNotifyWithExtras(isAllowVoice, isAllowVibrate, R.mipmap.ic_launcher, tickerText, contentTitle, tickerText.toString(), paramIntent);
                }

            }
        }
    }
}
