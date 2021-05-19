package com.haier.cellarette.baselibrary.liandong.demo2.bean;

/**
 * Created by lingdong on 2017/12/5.
 */

public class Liandong2FoodBean {

    private boolean isGroup;
    private int groupId = -1;  //组号
    private String groupTitle;

    private int imageId;
    private String title;

    public boolean getGroupId() {
        return isGroup;
    }

    public void setGroupId(boolean groupId) {
        isGroup = groupId;
    }

    public int getGroup() {
        return groupId;
    }

    public void setGroup(int group) {
        this.groupId = group;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
