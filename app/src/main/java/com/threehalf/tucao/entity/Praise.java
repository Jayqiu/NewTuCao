package com.threehalf.tucao.entity;

import cn.bmob.v3.BmobObject;

/**
 * @author jayqiu
 * 点赞
 */
public class Praise extends BmobObject{
    private int praiseStatus;
    private TuCao praiseTuCao;
    private String tucaoId;
    private User user;
    private String userId;

    public void setPraiseStatus(int praiseStatus) {
        this.praiseStatus = praiseStatus;
    }

    public void setPraiseTuCao(TuCao praiseTuCao) {
        this.praiseTuCao = praiseTuCao;
    }

    public void setTucaoId(String tucaoId) {
        this.tucaoId = tucaoId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPraiseStatus() {
        return praiseStatus;
    }

    public TuCao getPraiseTuCao() {
        return praiseTuCao;
    }

    public String getTucaoId() {
        return tucaoId;
    }

    public User getUser() {
        return user;
    }

    public String getUserId() {
        return userId;
    }
}
