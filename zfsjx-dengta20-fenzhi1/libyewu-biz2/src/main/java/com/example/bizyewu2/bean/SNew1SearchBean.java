package com.example.bizyewu2.bean;

import java.io.Serializable;
import java.util.List;


public class SNew1SearchBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<SNew1SearchBean1> result;

    public SNew1SearchBean() {
    }

    public List<SNew1SearchBean1> getResult() {
        return result;
    }

    public void setResult(List<SNew1SearchBean1> result) {
        this.result = result;
    }
}
