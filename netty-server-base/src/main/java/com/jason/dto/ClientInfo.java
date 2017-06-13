package com.jason.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 13-12-23
 * Time: 下午4:55
 * 客户端信息
 */
public class ClientInfo implements Serializable{

    //客户端版本
    private String version;
    //子渠道号码
    private String channelid;
    //软件语言
    private String language;
    //客户端联网方式
    private String apn;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getApn() {
        return apn;
    }

    public void setApn(String apn) {
        this.apn = apn;
    }
}
