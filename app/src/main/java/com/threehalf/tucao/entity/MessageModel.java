package com.threehalf.tucao.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/7/1.
 */
public class MessageModel implements Serializable {
    private String alert;
    private String fId;
    private String ft;
    private String mc;
    private String mt;
    private String tag;
    private String tid;

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public void setFt(String ft) {
        this.ft = ft;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public void setMt(String mt) {
        this.mt = mt;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getAlert() {
        return alert;
    }

    public String getfId() {
        return fId;
    }

    public String getFt() {
        return ft;
    }

    public String getMc() {
        return mc;
    }

    public String getMt() {
        return mt;
    }

    public String getTag() {
        return tag;
    }

    public String getTid() {
        return tid;
    }
}
