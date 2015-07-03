package com.threehalf.tucao.entity;

import cn.bmob.v3.BmobObject;

/**
 * @author jayqiu
 */
public class Feedback extends BmobObject{
    private String feedbackContent;
    private User userInfo;

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }
}
