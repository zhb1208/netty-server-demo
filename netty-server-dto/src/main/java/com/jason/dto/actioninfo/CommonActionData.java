package com.jason.dto.actioninfo;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 13-12-24
 * Time: 上午9:32
 * To change this template use File | Settings | File Templates.
 */
public class CommonActionData {

    private String date;
    private String address;
    private String body;
    private String smstype;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public String getSmstype() {
        return smstype;
    }

    public void setSmstype(String smstype) {
        this.smstype = smstype;
    }
}
