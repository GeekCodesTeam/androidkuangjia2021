package com.example.bizyewu2.bean;

import java.io.Serializable;


public class SPolyvList1Detail1Bean1 implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String channelId;
    private String status;
    private String liveName;
    private String publisher;
    private String startTime;
    private String description;
    private String authType;
    private String coverImg;
    private String organizerName;
    private String m3u8Url;
    private String userId;
    private String shareUrl;

    public SPolyvList1Detail1Bean1() {
    }

    public SPolyvList1Detail1Bean1(String id, String channelId, String status, String liveName, String publisher, String startTime, String description, String authType, String coverImg, String organizerName, String m3u8Url, String userId, String shareUrl) {
        this.id = id;
        this.channelId = channelId;
        this.status = status;
        this.liveName = liveName;
        this.publisher = publisher;
        this.startTime = startTime;
        this.description = description;
        this.authType = authType;
        this.coverImg = coverImg;
        this.organizerName = organizerName;
        this.m3u8Url = m3u8Url;
        this.userId = userId;
        this.shareUrl = shareUrl;
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

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLiveName() {
        return liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getM3u8Url() {
        return m3u8Url;
    }

    public void setM3u8Url(String m3u8Url) {
        this.m3u8Url = m3u8Url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}
