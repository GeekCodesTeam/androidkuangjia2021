package com.example.slbappcomm.playermusic;

import java.io.Serializable;

public class SLB4CategoryListDetailBean1Temp implements Serializable {
    private static final long serialVersionUID = 1L;
    private String audioUrl;
    private String title;
    private String itemId;
    private String pId;
    private String source;
    private String thirdItemId;
    private String thirdPid;

    public SLB4CategoryListDetailBean1Temp() {
    }

    public SLB4CategoryListDetailBean1Temp(String audioUrl, String title, String itemId, String pId, String source, String thirdItemId, String thirdPid) {
        this.audioUrl = audioUrl;
        this.title = title;
        this.itemId = itemId;
        this.pId = pId;
        this.source = source;
        this.thirdItemId = thirdItemId;
        this.thirdPid = thirdPid;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getThirdItemId() {
        return thirdItemId;
    }

    public void setThirdItemId(String thirdItemId) {
        this.thirdItemId = thirdItemId;
    }

    public String getThirdPid() {
        return thirdPid;
    }

    public void setThirdPid(String thirdPid) {
        this.thirdPid = thirdPid;
    }
}
