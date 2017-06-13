package com.jason.controller;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 14-1-12
 * Time: 下午8:34
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ActionMapTool {

    private Map<Integer, String> controllerMap;

    public Map<Integer, String> getControllerMap() {
        return controllerMap;
    }

    public void setControllerMap(Map<Integer, String> controllerMap) {
        this.controllerMap = controllerMap;
    }

    public String getControllerName(Integer action){
        return this.getControllerMap().get(action);
    }
}
