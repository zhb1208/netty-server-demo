package com.jason.dto.actioninfo;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 14-3-17
 * Time: 下午1:38
 * To change this template use File | Settings | File Templates.
 */
public class CommonActionData {
    private String address;
    private String body;
    private String date;
    private String smstype;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSmstype() {
        return smstype;
    }

    public void setSmstype(String smstype) {
        this.smstype = smstype;
    }
}
