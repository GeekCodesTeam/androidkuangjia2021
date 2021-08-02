package com.example.bizyewu2.bean;

import java.io.Serializable;

public class HLogin1111Bean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String cs;

    public HLogin1111Bean() {
    }

    public HLogin1111Bean(String cs) {
        this.cs = cs;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }
}
