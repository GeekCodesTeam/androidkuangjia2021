package com.example.bizyewu2.bean;

import java.io.Serializable;
import java.util.List;


public class SPolyvList1Bean1 implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String liveName;
    private String channelId;
    private String organizerName;
    private String coverImg;
    private String publisher;
    private String startTime;
    private String status;
    private String authType;
    private String playbackVideoId;
    private String qrCordPath;

    public SPolyvList1Bean1() {
    }

    public SPolyvList1Bean1(String id, String liveName, String channelId, String organizerName, String coverImg, String publisher, String startTime, String status, String authType, String playbackVideoId, String qrCordPath) {
        this.id = id;
        this.liveName = liveName;
        this.channelId = channelId;
        this.organizerName = organizerName;
        this.coverImg = coverImg;
        this.publisher = publisher;
        this.startTime = startTime;
        this.status = status;
        this.authType = authType;
        this.playbackVideoId = playbackVideoId;
        this.qrCordPath = qrCordPath;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLiveName() {
        return liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getPlaybackVideoId() {
        return playbackVideoId;
    }

    public void setPlaybackVideoId(String playbackVideoId) {
        this.playbackVideoId = playbackVideoId;
    }

    public String getQrCordPath() {
        return qrCordPath;
    }

    public void setQrCordPath(String qrCordPath) {
        this.qrCordPath = qrCordPath;
    }
}
