package com.jason.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 13-12-23
 * Time: 下午4:58
 * Action 信息
 */
public class ActionInfo implements Serializable {
    //命令返回代码
    private Integer code;
    //命令id
    private Integer value;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
