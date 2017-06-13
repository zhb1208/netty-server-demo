package com.jason.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 13-12-23
 * Time: 下午4:50
 * 客户端系统信息
 */
public class SystemInfo implements Serializable {

    //手机号码
    private String phonenum;
    //Sim卡串号
    private String imsi;
    //手机设备号
    private String imei;
    //手机信号
    private String device;
    //手机品牌
    private String brand;
    //系统类型与描述
    private String os;
    //小区基站ID
    private String cellid;
    //分辨率
    private String resolution;
    //系统语言
    private String language;
    //PN注册唯一ID
    private String pnid;
    //pn类型
    private String pntype;

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getCellid() {
        return cellid;
    }

    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPnid() {
        return pnid;
    }

    public void setPnid(String pnid) {
        this.pnid = pnid;
    }

    public String getPntype() {
        return pntype;
    }

    public void setPntype(String pntype) {
        this.pntype = pntype;
    }
}
