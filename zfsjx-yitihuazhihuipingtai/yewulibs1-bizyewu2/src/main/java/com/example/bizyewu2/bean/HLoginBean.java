package com.example.bizyewu2.bean;

import java.io.Serializable;

public class HLoginBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String expire;
    private String token;

    public HLoginBean() {
    }

    public HLoginBean(String expire, String token) {
        this.expire = expire;
        this.token = token;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
