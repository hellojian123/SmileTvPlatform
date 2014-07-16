package com.smiletv.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by hejian on 2014/7/14.
 * 封装客户端JSON请求参数
 */
public class RequestParam implements Serializable{

    private String userName;     //用户名
    private String deviceID;     //设备ID
    private String uniqueID ;    //uniqueID
    private String userPhone;    //用户电话
    private String password;     //密码
    private String randomKey;    //客户端随机字符串
    private String requestType;  //请求类型
    private String[] params;     //请求参数

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "RequestParam{" +
                "userName='" + userName + '\'' +
                ", deviceID='" + deviceID + '\'' +
                ", uniqueID='" + uniqueID + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", password='" + password + '\'' +
                ", randomkey='" + randomKey + '\'' +
                ", requestType='" + requestType + '\'' +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
