package com.haier.cellarette.libretrofit.common;

public class ResponseSlbBean<T> {
    private int code;
    private String msg;
    private T data;

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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("code: ").append(this.code).append("\n");
        sb.append("msg: ").append(this.msg).append("\n");
        sb.append("data: ").append(this.data).append("\n");
        return sb.toString();
    }
}
