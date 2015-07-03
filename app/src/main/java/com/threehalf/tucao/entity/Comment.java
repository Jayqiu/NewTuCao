package com.threehalf.tucao.entity;

import cn.bmob.v3.BmobObject;

/**
 * @author jayqiu
 */
public class Comment extends BmobObject{
    private String commentContent;
    private int commentType;
    private String replyUserNick;
    private TuCao tuCao;
    private User userInfo;

    public String getCommentContent() {
        return commentContent;
    }

    public int getCommentType() {
        return commentType;
    }

    public String getReplyUserNick() {
        return replyUserNick;
    }

    public TuCao getTuCao() {
        return tuCao;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public void setReplyUserNick(String replyUserNick) {
        this.replyUserNick = replyUserNick;
    }

    public void setTuCao(TuCao tuCao) {
        this.tuCao = tuCao;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }
}
