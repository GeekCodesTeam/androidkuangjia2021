/*
 * *********************************************************
 *   author   yangqilin
 *   company  fosung
 *   email    yql19911010@126.com
 *   date     19-1-18 下午4:46
 * ********************************************************
 */

package com.fosung.xuanchuanlan.mian.http.entity;


import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

import java.util.List;

public class AppsListReply extends AppsBaseReplyBean {

    /**
     * data : {"menuList":[{"name":"推荐栏目","id":"3353514853619736312","sort":1,"appList":[{"grantId":"3356391972041399652","appId":"3351373054948154226","name":"盒子平台测试","icon":"","menuId":"3353514853619736312","id":"3356391972041404996","sort":1}]}],"videoList":[{"name":"视频上传","id":"3355735869179836536","type":"PUBLIC","videoName":"d.mp4","playUrl":"http://127.0.0.1:82/box/boxVideo/publicVideo/20190709//3355802980191319903.mp4"}]}
     */

    public DataBean data;

    public static class DataBean {
        public List<MenuListBean> menuList;
        public List<VideoListBean> videoList;

        public static class MenuListBean {
            /**
             * name : 推荐栏目
             * id : 3353514853619736312
             * sort : 1
             * appList : [{"grantId":"3356391972041399652","appId":"3351373054948154226","name":"盒子平台测试","icon":"","menuId":"3353514853619736312","id":"3356391972041404996","sort":1}]
             */

            public String name;
            public String id;
            public int sort;
            public boolean ischeck;
            public List<AppListBean> appList;

            public static class AppListBean {
                /**
                 * grantId : 3356391972041399652
                 * appId : 3351373054948154226
                 * name : 盒子平台测试
                 * icon :
                 * menuId : 3353514853619736312
                 * id : 3356391972041404996
                 * sort : 1
                 */

                public String grantId;
                public String code;
                public String icon;
                public String appId;
                public String name;
                public String url;
                public String menuId;
                public String id;
                public int sort;
            }
        }

        public static class VideoListBean {
            /**
             * name : 视频上传
             * id : 3355735869179836536
             * type : PUBLIC
             * videoName : d.mp4
             * playUrl : http://127.0.0.1:82/box/boxVideo/publicVideo/20190709//3355802980191319903.mp4
             */

            public String name;
            public String id;
            public String type;
            public String videoName;
            public String playUrl;
        }
    }
}
