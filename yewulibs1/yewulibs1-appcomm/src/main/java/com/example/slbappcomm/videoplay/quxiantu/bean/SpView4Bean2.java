package com.example.slbappcomm.videoplay.quxiantu.bean;

import java.util.LinkedList;

public class SpView4Bean2 {
    private String title;
    private LinkedList<SpView4Bean21> labels21;

    public SpView4Bean2() {
    }

    public SpView4Bean2(String title, LinkedList<SpView4Bean21> labels21) {
        this.title = title;
        this.labels21 = labels21;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LinkedList<SpView4Bean21> getLabels21() {
        return labels21;
    }

    public void setLabels21(LinkedList<SpView4Bean21> labels21) {
        this.labels21 = labels21;
    }
}
