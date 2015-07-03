package com.threehalf.tucao.entity;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.datatype.BmobRelation;

public class User extends BmobChatUser {

	private static final long serialVersionUID = 1L;
	private String signature;
	private String vocation;
	private String sortLetters;
	private String address;
	private BmobRelation blogs;
	private BmobGeoPoint location;//
	private boolean sex;
	private long tucaoNum;
	private long tucaoGrade;
	private String birthday;
	private BmobRelation tucao;
	private BmobRelation commet;
	private BmobRelation feedback;
	private BmobRelation praise;
	private long gold;

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getVocation() {
		return vocation;
	}

	public void setVocation(String vocation) {
		this.vocation = vocation;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public BmobRelation getBlogs() {
		return blogs;
	}

	public void setBlogs(BmobRelation blogs) {
		this.blogs = blogs;
	}

	public BmobGeoPoint getLocation() {
		return location;
	}

	public void setLocation(BmobGeoPoint location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public BmobRelation getTucao() {
		return tucao;
	}

	public void setTucao(BmobRelation tucao) {
		this.tucao = tucao;
	}

	public BmobRelation getCommet() {
		return commet;
	}

	public void setCommet(BmobRelation commet) {
		this.commet = commet;
	}

	public long getTucaoNum() {
		return tucaoNum;
	}

	public void setTucaoNum(long tucaoNum) {
		this.tucaoNum = tucaoNum;
	}

	public long getTucaoGrade() {
		return tucaoGrade;
	}

	public void setTucaoGrade(long tucaoGrade) {
		this.tucaoGrade = tucaoGrade;
	}

	public BmobRelation getFeedback() {
		return feedback;
	}

	public void setFeedback(BmobRelation feedback) {
		this.feedback = feedback;
	}

	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}

	public BmobRelation getPraise() {
		return praise;
	}

	public void setPraise(BmobRelation praise) {
		this.praise = praise;
	}
	
}
