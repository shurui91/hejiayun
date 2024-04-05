package com.msb.hjycommunity.common.core.domain;

import java.io.Serializable;

/**
 * 响应结果封装对象
 *
 * @param <T>
 */
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 响应状态码
     * 这里修改成int类型，是因为前端接收到的是int类型的code，如果是String类型，前端接收到的是字符串，会有问题
     */
    //private String code;
    private int code;

    /**
     * 响应结果描述
     */
    private String msg;

    /**
     * 返回的数据
     */
    private T data;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 成功返回
     *
     * @param data
     * @return: com.msb.hjycommunity.common.core.domain.BaseResponse<T>
     */
    public static <T> BaseResponse<T> success(T data) {
        BaseResponse response = new BaseResponse();
        response.setCode(Integer.parseInt(ResultCode.SUCCESS.getCode()));
        response.setMsg(ResultCode.SUCCESS.getMessage());
        response.setSuccess(true);
        response.setData(data);

        return response;
    }

    /**
     * 失败返回 一个参数.
     *
     * @param message
     * @return: com.msb.hjycommunity.common.core.domain.BaseResponse<T>
     */
    public static <T> BaseResponse<T> fail(String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(Integer.parseInt(ResultCode.ERROR.getCode()));
        response.setMsg(message);

        return response;
    }

    /**
     * 失败返回 两个参数·
     *
     * @param code
     * @param message
     * @return: com.msb.hjycommunity.common.core.domain.BaseResponse<T>
     */
    public static <T> BaseResponse<T> fail(String code, String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(Integer.parseInt(code));
        response.setMsg(message);

        return response;
    }

    /**
     * 失败返回 三个参数·
     *
     * @param code
     * @param message
     * @return: com.msb.hjycommunity.common.core.domain.BaseResponse<T>
     */
    public static <T> BaseResponse<T> fail(String code, String message, boolean success) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(Integer.parseInt(code));
        response.setMsg(message);
        response.setSuccess(success);
        return response;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
