package com.jason.command;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 13-12-23
 * Time: 下午10:19
 * To change this template use File | Settings | File Templates.
 */
public enum CommandEnum {
    ACTIVED(1001, "active"),
    CLOUDSCAN(1002, "cloudScan"),
    REGISTER(1003, "register");

    private Integer code;
    private String desc;

    // 构造方法
    private CommandEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}