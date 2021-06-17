package com.example.libbase.agentwebview.bean;

import java.io.Serializable;
import java.util.List;

public class HCategoryBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<HCategoryBean1> list;//

    public HCategoryBean() {
    }

    public HCategoryBean(List<HCategoryBean1> list) {
        this.list = list;
    }

    public List<HCategoryBean1> getList() {
        return list;
    }

    public void setList(List<HCategoryBean1> list) {
        this.list = list;
    }
}
