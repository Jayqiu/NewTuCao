package com.threehalf.tucao.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/7/1.
 */
public class AlertEntity  implements Serializable
{
    private String msg;
    private String pType;  // 1000 系统推荐
    private String tObj;

    public String getMsg()
    {
        return this.msg;
    }

    public String getpType()
    {
        return this.pType;
    }

    public String gettObj()
    {
        return this.tObj;
    }

    public void setMsg(String paramString)
    {
        this.msg = paramString;
    }

    public void setpType(String paramString)
    {
        this.pType = paramString;
    }

    public void settObj(String paramString)
    {
        this.tObj = paramString;
    }
}
