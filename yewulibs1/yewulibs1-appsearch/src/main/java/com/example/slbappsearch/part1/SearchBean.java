package com.example.slbappsearch.part1;

import java.io.Serializable;

public class SearchBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String text_id;
    private String text_content;
    private boolean enselect = false;

    public SearchBean() {
    }

    public SearchBean(String text_id, String text_content, boolean enselect) {
        this.text_id = text_id;
        this.text_content = text_content;
        this.enselect = enselect;
    }

    public String getText_id() {
        return text_id;
    }

    public void setText_id(String text_id) {
        this.text_id = text_id;
    }

    public String getText_content() {
        return text_content;
    }

    public void setText_content(String text_content) {
        this.text_content = text_content;
    }

    public boolean isEnselect() {
        return enselect;
    }

    public void setEnselect(boolean enselect) {
        this.enselect = enselect;
    }
}
