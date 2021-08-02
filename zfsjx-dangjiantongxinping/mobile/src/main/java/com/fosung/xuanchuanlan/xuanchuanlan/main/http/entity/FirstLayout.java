package com.fosung.xuanchuanlan.xuanchuanlan.main.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

import java.util.List;

/**
 * Created by fosung on 2019/9/24.
 */

public class FirstLayout extends AppsBaseReplyBean {

    public Layout data;


    public static class Layout {
        //    public String id;
//    public String name;         //授权模板
//    public String project;      //我的项目


        public String terminal;     //box
        public String Resolution;   //"1920*1280"
        public boolean isshowmenu; //是否有栏目
//        public boolean isshownotice; //是否有通知栏
        public float version;   //版本
        public List<Common> commonList;
    }


    public static class Common {
        public String type;       //类型
        public String id;
        public String code;
        public String name;    //文本内容
        public String image;    //图片链接
        public int height;
        public int width;
        public String x;
        public String y;
        public String style;
        public String function;
        public int sort;
        public String videourl; //视频连接

        public String url; //网址链接

        public String content;
        public boolean check;   //栏目是否选中
        public boolean videonews;
        public boolean medialibrary;
        public List<Common> appList;

    }


    public static class Style {
        public String color;
        public String fontsize;
        public String foemat;
        public String initcolor;
        public String checkedcolor;

        public String normalPic;
        public String pressPic;
    }

    public static class AnnouncementStyle {
        public String size;
        public String lines;
        public String words;
        public String announ;
        public String workstatus;
    }

    public static class Function {
        public String url;
    }

}
