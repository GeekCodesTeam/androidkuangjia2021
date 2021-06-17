package com.example.libbase.agentwebview.bean;

import java.io.Serializable;

public class HCategoryBean1 implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;//
    private String name;//

    public HCategoryBean1() {
    }

    public HCategoryBean1(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
