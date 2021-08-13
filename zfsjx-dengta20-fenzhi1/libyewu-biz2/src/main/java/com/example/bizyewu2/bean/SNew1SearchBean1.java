package com.example.bizyewu2.bean;

import java.io.Serializable;

// {
//         "coverImg": "https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverA.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600",
//         "bannerImg": "https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/16/54/coverB.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600",
//         "bookName": "年的故事",
//         "bookNameHtml": "年的故事",
//         "vertical": false,
//         "readmode": "across",
//         "bookId": "",
//         "bookItemId": "54",
//         "sourceType": "bookItem",
//         "cornerType": "",
//         "readCount": "",
//         "itemCount": "",bookItem
//         "collect": "",
//         "orders": 1
//         },
public class SNew1SearchBean1 implements Serializable {
    private static final long serialVersionUID = 1L;
    private String coverImg;
    private String bannerImg;
    private SNew1SearchBean2 cornerMap;
    private String descr;
    private boolean free;
    private String hios;
    private String itemCount;
    private String itemCountStr;
    private String itemId;
    private String pId;
    private String pricePkg;
    private String pricePkgVip;
    private String priceStr;
    private String sourceType;
    private String title;
    private String titleHtml;
    private String viewCountStr;

    public SNew1SearchBean1() {
    }

    public SNew1SearchBean1(String coverImg, String bannerImg, SNew1SearchBean2 cornerMap, String descr, boolean free, String hios, String itemCount, String itemCountStr, String itemId, String pId, String pricePkg, String pricePkgVip, String priceStr, String sourceType, String title, String titleHtml, String viewCountStr) {
        this.coverImg = coverImg;
        this.bannerImg = bannerImg;
        this.cornerMap = cornerMap;
        this.descr = descr;
        this.free = free;
        this.hios = hios;
        this.itemCount = itemCount;
        this.itemCountStr = itemCountStr;
        this.itemId = itemId;
        this.pId = pId;
        this.pricePkg = pricePkg;
        this.pricePkgVip = pricePkgVip;
        this.priceStr = priceStr;
        this.sourceType = sourceType;
        this.title = title;
        this.titleHtml = titleHtml;
        this.viewCountStr = viewCountStr;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public SNew1SearchBean2 getCornerMap() {
        return cornerMap;
    }

    public void setCornerMap(SNew1SearchBean2 cornerMap) {
        this.cornerMap = cornerMap;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public String getHios() {
        return hios;
    }

    public void setHios(String hios) {
        this.hios = hios;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemCountStr() {
        return itemCountStr;
    }

    public void setItemCountStr(String itemCountStr) {
        this.itemCountStr = itemCountStr;
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

    public String getPricePkg() {
        return pricePkg;
    }

    public void setPricePkg(String pricePkg) {
        this.pricePkg = pricePkg;
    }

    public String getPricePkgVip() {
        return pricePkgVip;
    }

    public void setPricePkgVip(String pricePkgVip) {
        this.pricePkgVip = pricePkgVip;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleHtml() {
        return titleHtml;
    }

    public void setTitleHtml(String titleHtml) {
        this.titleHtml = titleHtml;
    }

    public String getViewCountStr() {
        return viewCountStr;
    }

    public void setViewCountStr(String viewCountStr) {
        this.viewCountStr = viewCountStr;
    }
}
