package com.haier.cellarette.libretrofit.common;

public class ResponseSlbBean<T> {
    private int code;
    private String msg;
    private T data;
    private boolean success;

    public ResponseSlbBean() {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("code: ").append(this.code).append("\n");
        sb.append("msg: ").append(this.msg).append("\n");
        sb.append("data: ").append(this.data).append("\n");
        sb.append("success: ").append(this.success).append("\n");
        return sb.toString();
    }
}
