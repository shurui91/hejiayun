package com.msb.hjycommunity.common.core.exception;

import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletResponse;

/**
 * 业务异常基类
 */
public class CustomException extends RuntimeException {
    /**
     * 状态码
     */
    private int code;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 承载数据
     */
    private T data;

    /**
     * 返回消息
     */
    private String msg;

    public CustomException() {
    }

    public CustomException(int code, boolean success, T data, String msg) {
        this.code = code;
        this.success = success;
        this.data = data;
        this.msg = msg;
    }

    public CustomException(String msg, int code) {
        this.msg = msg;
        this.code = code;
        this.success = HttpServletResponse.SC_OK == code;
    }

    public CustomException(int code, boolean success, String msg) {
        this.code = code;
        this.success = success;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
