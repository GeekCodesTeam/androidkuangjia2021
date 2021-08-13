package com.fosung.xuanchuanlan.mian.http.entity;

import java.util.List;

public class VideoSelectEvent {
    private List videoList;
    private Integer selectedIndex;
    private int videoPosition;

    public VideoSelectEvent(List videoList, Integer selectedIndex, int videoPosition) {
        this.videoList = videoList;
        this.selectedIndex = selectedIndex;
        this.videoPosition = videoPosition;
    }

    public List getVideoList() {
        return videoList;
    }

    public Integer getSelectedIndex() {
        return selectedIndex;
    }

    public int getVideoPosition() {
        return videoPosition;
    }
}
