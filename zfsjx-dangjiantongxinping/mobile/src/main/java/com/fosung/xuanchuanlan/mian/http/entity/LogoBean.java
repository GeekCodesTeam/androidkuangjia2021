package com.fosung.xuanchuanlan.mian.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

import java.util.List;

public class LogoBean extends AppsBaseReplyBean{

    public List<DataBean> data;

    public static class DataBean {
        /**
         * imagUrl : http://127.0.0.1:82/box/box/boxImage/adImage/3356512819267773670.jpg
         */

        public String imagUrl;
    }
}
