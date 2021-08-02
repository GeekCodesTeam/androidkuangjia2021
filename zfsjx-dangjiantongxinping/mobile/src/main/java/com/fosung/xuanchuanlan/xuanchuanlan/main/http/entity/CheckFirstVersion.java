package com.fosung.xuanchuanlan.xuanchuanlan.main.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

/**
 * Created by fosung on 2019/9/24.
 */

public class CheckFirstVersion extends AppsBaseReplyBean {

    public PageVersion data;

    public class PageVersion{
        public float version;
    }

}
