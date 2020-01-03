package com.yui.tool.dynamicdubbo.dto;

import java.io.Serializable;

/**
 * @author XuZhuohao
 * @date 2019/10/18
 */
public class ResultBean<T>  implements Serializable {
    private boolean isSuccess;
    private int responseCode;
    private String responseMsg;
    private T data;

    public ResultBean() {
    }

    public ResultBean<T> toSuccess(T data){
        this.setData(data);
        this.setResponseCode(0);
        this.setResponseMsg("操作成功！");
        this.setIsSuccess(true);
        return this;
    }
    public ResultBean<T> toFail(String responseMsg){
        return this.setResponseCode(-1).setResponseMsg(responseMsg).setIsSuccess(false);
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public ResultBean<T> setIsSuccess(boolean success) {
        isSuccess = success;
        return this;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public ResultBean<T> setResponseCode(int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public ResultBean<T> setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultBean<T> setData(T data) {
        this.data = data;
        return this;
    }
}
