package com.example.bizyewu2.bean;

import java.io.Serializable;

public class HUserInfoBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String childAvatar;// 宝宝-头像url
    private String childGender;// 宝宝-性别 1男 2女 0未知
    private String nikeName;// 昵称
    private String phone;// 手机号

    public HUserInfoBean() {
    }

    public HUserInfoBean(String childAvatar, String childGender, String nikeName, String phone) {
        this.childAvatar = childAvatar;
        this.childGender = childGender;
        this.nikeName = nikeName;
        this.phone = phone;
    }

    public String getChildAvatar() {
        return childAvatar;
    }

    public void setChildAvatar(String childAvatar) {
        this.childAvatar = childAvatar;
    }

    public String getChildGender() {
        return childGender;
    }

    public void setChildGender(String childGender) {
        this.childGender = childGender;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
