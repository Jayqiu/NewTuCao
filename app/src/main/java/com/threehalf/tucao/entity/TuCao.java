package com.threehalf.tucao.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
public class TuCao extends BmobObject {

	private static final long serialVersionUID = 1L;
	private BmobFile tucaoImg;
	private String userId;
	private String tucaoContent;
	private long commentNum;

	private int isAnonymous;
	private long praiseNum;
	private User userInfo;
	private BmobRelation commet;
	private BmobRelation tuPraise;
	private int praiseStatus;
	private int tucaoStatus;// 吐槽状态 0 没有通过审核，1,通过审核
	



	public BmobFile getTucaoImg() {
		return tucaoImg;
	}

	public void setTucaoImg(BmobFile tucaoImg) {
		this.tucaoImg = tucaoImg;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTucaoContent() {
		return tucaoContent;
	}

	public void setTucaoContent(String tucaoContent) {
		this.tucaoContent = tucaoContent;
	}

	public long getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(long commentNum) {
		this.commentNum = commentNum;
	}

	public int getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(int isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public long getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(long praiseNum) {
		this.praiseNum = praiseNum;
	}

	public User getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}

	public int getPraiseStatus() {
		return praiseStatus;
	}

	public void setPraiseStatus(int praiseStatus) {
		this.praiseStatus = praiseStatus;
	}

	public BmobRelation getCommet() {
		return commet;
	}

	public void setCommet(BmobRelation commet) {
		this.commet = commet;
	}

	public BmobRelation getTuPraise() {
		return tuPraise;
	}

	public void setTuPraise(BmobRelation tuPraise) {
		this.tuPraise = tuPraise;
	}

	public int getTucaoStatus() {
		return tucaoStatus;
	}

	public void setTucaoStatus(int tucaoStatus) {
		this.tucaoStatus = tucaoStatus;
	}
	

}
