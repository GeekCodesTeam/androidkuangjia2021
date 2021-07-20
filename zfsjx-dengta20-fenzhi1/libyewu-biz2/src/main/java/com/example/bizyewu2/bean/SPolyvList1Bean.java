package com.example.bizyewu2.bean;

import java.io.Serializable;
import java.util.List;


public class SPolyvList1Bean implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<SPolyvList1Bean1> datalist;

    public SPolyvList1Bean() {
    }

    public SPolyvList1Bean(List<SPolyvList1Bean1> datalist) {
        this.datalist = datalist;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<SPolyvList1Bean1> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<SPolyvList1Bean1> datalist) {
        this.datalist = datalist;
    }
}
