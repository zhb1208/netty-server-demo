package com.jason.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 13-12-24
 * Time: 上午9:44
 * To change this template use File | Settings | File Templates.
 */
public class BaseReplyDto implements Serializable {

    private ActionInfo actionInfo;
    private Map<String, String> data;

    public ActionInfo getActionInfo() {
        return actionInfo;
    }

    public void setActionInfo(ActionInfo actionInfo) {
        this.actionInfo = actionInfo;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
