package com.example.bizyewu2.bean;

import java.io.Serializable;
import java.util.List;


public class SSearchBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<SSearchBean1> result;

    public SSearchBean() {
    }

    public SSearchBean(List<SSearchBean1> result) {
        this.result = result;
    }

    public List<SSearchBean1> getResult() {
        return result;
    }

    public void setResult(List<SSearchBean1> result) {
        this.result = result;
    }
}
