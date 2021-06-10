package com.example.slbappcomm.polyv;

import java.io.Serializable;

public class PolyvBean1 implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String url;
    private boolean enable;

    public PolyvBean1() {
    }

    public PolyvBean1(String id, String name, String url, boolean enable) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.enable = enable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
