package com.smiletv.bean;

import java.io.Serializable;

/**
 * Created by hejian on 2014/7/9.
 * 组装返回给客户端响应内容的bean
 */
public class ResponseResult implements Serializable {

    private Integer result;       //请求的结果码   0代表成功，-1代表失败
    private String requestType;   //请求的业务类型
    private String[] content;     //请求结果内容

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = content;
    }
}
