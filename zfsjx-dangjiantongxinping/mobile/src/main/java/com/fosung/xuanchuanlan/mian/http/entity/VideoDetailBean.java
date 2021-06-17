package com.fosung.xuanchuanlan.mian.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

import java.util.List;

public class VideoDetailBean extends AppsBaseReplyBean {

    /**
     * data : {"PUBLIC":[{"id":"3356543405332118719","name":"党建学习","playUrl":"http://10.254.23.68:82/box/boxVideo/publicVideo/20190713//3356543403176235668.mp4","videoName":"a.mp4","type":"PUBLIC"}],"PRIVATE":[{"id":"3356543405332118719","name":"党建学习","playUrl":"http://10.254.23.68:82/box/boxVideo/publicVideo/20190713//3356543403176235668.mp4","videoName":"a.mp4","type":"PRIVATE"}]}
     */

    public DataBean data;

    public static class DataBean {
        public List<PUBLICBean> PUBLIC;
        public List<PRIVATEBean> PRIVATE;

        public static class PUBLICBean {
            /**
             * id : 3356543405332118719
             * name : 党建学习
             * playUrl : http://10.254.23.68:82/box/boxVideo/publicVideo/20190713//3356543403176235668.mp4
             * videoName : a.mp4
             * type : PUBLIC
             */

            public String id;
            public String name;
//            public String playUrl;
            public String videoName;
            public String storePath;
            public String type;
            public boolean isPlay;
        }

        public static class PRIVATEBean {
            /**
             * id : 3356543405332118719
             * name : 党建学习
             * playUrl : http://10.254.23.68:82/box/boxVideo/publicVideo/20190713//3356543403176235668.mp4
             * videoName : a.mp4
             * type : PRIVATE
             */

            public String id;
            public String name;
//            public String playUrl;
            public String videoName;
            public String storePath;
            public String type;
            public boolean isPlay;
        }
    }
}
