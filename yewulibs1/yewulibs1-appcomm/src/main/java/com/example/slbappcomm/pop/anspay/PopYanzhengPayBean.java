package com.example.slbappcomm.pop.anspay;

//import android.support.annotation.DrawableRes;

import androidx.annotation.DrawableRes;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class PopYanzhengPayBean {
    private boolean isRetweet;
    private int id;
    private String content;
    private @DrawableRes
    int avatar;

    public PopYanzhengPayBean() {
    }

    public PopYanzhengPayBean(boolean isRetweet, int id, String content, int avatar) {
        this.isRetweet = isRetweet;
        this.id = id;
        this.content = content;
        this.avatar = avatar;
    }

    public boolean isRetweet() {
        return isRetweet;
    }

    public void setRetweet(boolean retweet) {
        isRetweet = retweet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
