package com.haier.cellarette.libretrofit.common;

public class ResponseSlbBean1<T> {
    /**
     * code : 响应码
     * msg : 消息
     * result : 结果
     * success : 是否成功
     */

    private int code;
    private int errorCode;
    private String message;
    private String msg;
    private T result;
    private boolean success;


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
