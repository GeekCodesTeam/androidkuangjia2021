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
public class SSearchBean1 implements Serializable {
    private static final long serialVersionUID = 1L;
    private String coverImg;
    private String bannerImg;
    private String bookName;
    private String bookNameHtml;
    private boolean vertical;
    private String bookId;
    private String bookItemId;
    private String sourceType;
    private String hios;
    private String cornerType;
    private String readCount;
    private String itemCount;
    private String collect;
    private String orders;

    public SSearchBean1() {
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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookNameHtml() {
        return bookNameHtml;
    }

    public void setBookNameHtml(String bookNameHtml) {
        this.bookNameHtml = bookNameHtml;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookItemId() {
        return bookItemId;
    }

    public void setBookItemId(String bookItemId) {
        this.bookItemId = bookItemId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getHios() {
        return hios;
    }

    public void setHios(String hios) {
        this.hios = hios;
    }

    public String getCornerType() {
        return cornerType;
    }

    public void setCornerType(String cornerType) {
        this.cornerType = cornerType;
    }

    public String getReadCount() {
        return readCount;
    }

    public void setReadCount(String readCount) {
        this.readCount = readCount;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getCollect() {
        return collect;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }
}
