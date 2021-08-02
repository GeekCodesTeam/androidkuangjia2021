package com.example.slbappcomm.videoplay.quxiantu.bean;

import java.util.LinkedList;

public class SpView4Bean {
    private String title1;
    private String title2;
    private int title21;
    private String title3;
    private int title31;
    private LinkedList<String> labels1;
    private SpView4Bean2 labels2;

    public SpView4Bean() {
    }

    public SpView4Bean(String title1, String title2, int title21, String title3, int title31, LinkedList<String> labels1, SpView4Bean2 labels2) {
        this.title1 = title1;
        this.title2 = title2;
        this.title21 = title21;
        this.title3 = title3;
        this.title31 = title31;
        this.labels1 = labels1;
        this.labels2 = labels2;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public int getTitle21() {
        return title21;
    }

    public void setTitle21(int title21) {
        this.title21 = title21;
    }

    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    public int getTitle31() {
        return title31;
    }

    public void setTitle31(int title31) {
        this.title31 = title31;
    }

    public LinkedList<String> getLabels1() {
        return labels1;
    }

    public void setLabels1(LinkedList<String> labels1) {
        this.labels1 = labels1;
    }

    public SpView4Bean2 getLabels2() {
        return labels2;
    }

    public void setLabels2(SpView4Bean2 labels2) {
        this.labels2 = labels2;
    }
}
