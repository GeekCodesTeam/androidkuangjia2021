package com.example.slbappindex.adapters;

import java.io.Serializable;

public class ShouyeF4Bean1 implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tab_id;
    private String tab_name;
    private boolean enable;

    public ShouyeF4Bean1() {
    }

    public ShouyeF4Bean1(String tab_id, String tab_name, boolean enable) {
        this.tab_id = tab_id;
        this.tab_name = tab_name;
        this.enable = enable;
    }

    public String getTab_id() {
        return tab_id;
    }

    public void setTab_id(String tab_id) {
        this.tab_id = tab_id;
    }

    public String getTab_name() {
        return tab_name;
    }

    public void setTab_name(String tab_name) {
        this.tab_name = tab_name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
