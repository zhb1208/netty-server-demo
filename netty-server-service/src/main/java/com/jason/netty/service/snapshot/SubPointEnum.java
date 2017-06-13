package com.jason.netty.service.snapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 17/6/11
 * Time: 下午4:14
 * To change this template use File | Settings | File Templates.
 */
public enum SubPointEnum {

    DECODEANDPARSEJSON("decodeAndParseJson",2),CLOUDSCAN("cloudscan",3),ACTIVE("active",4),REGISTER("register",5);
    private String name;
    private int index;

    private SubPointEnum(String name, int index){
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * 获取enum的name
     * @param index
     * @return
     */
    public static String getName(int index) {
        for (SubPointEnum s : SubPointEnum.values()) {
            if (s.getIndex() == index) {
                return s.name;
            }
        }
        return null;
    }

    /**
     * 获取enum的map
     * @return
     */
    public static Map getEnumMap() {
        Map map = new HashMap();
        for (SubPointEnum s : SubPointEnum.values()) {
            map.put(s.getName(),s.getIndex());
        }
        return map;
    }
}
