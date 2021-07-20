package com.haier.cellarette.libretrofit.common;

public class ResponseSlbBean2<T> {
    private String code;
    private String message;
    private T data;

    public ResponseSlbBean2() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
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
        sb.append("msg: ").append(this.message).append("\n");
        sb.append("data: ").append(this.data).append("\n");
        return sb.toString();
    }
}
