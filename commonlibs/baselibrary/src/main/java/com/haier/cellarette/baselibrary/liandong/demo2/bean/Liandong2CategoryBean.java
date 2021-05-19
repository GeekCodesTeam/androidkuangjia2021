package com.haier.cellarette.baselibrary.liandong.demo2.bean;

/**
 * Created by lingdong on 2017/12/5.
 */

public class Liandong2CategoryBean {

    private int groupId;    //对应groupID
    private String title;
    private boolean isChecked;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
