package com.example.slbappindex.adapters;

import java.io.Serializable;

public class ShouyeF4Bean1 implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String tab_id;
    private String tab_tag;
    private String tab_name;
    private boolean enable;

    public ShouyeF4Bean1() {
    }

    public ShouyeF4Bean1(String id, String tab_id, String tab_tag, String tab_name, boolean enable) {
        this.id = id;
        this.tab_id = tab_id;
        this.tab_tag = tab_tag;
        this.tab_name = tab_name;
        this.enable = enable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTab_id() {
        return tab_id;
    }

    public void setTab_id(String tab_id) {
        this.tab_id = tab_id;
    }

    public String getTab_tag() {
        return tab_tag;
    }

    public void setTab_tag(String tab_tag) {
        this.tab_tag = tab_tag;
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
